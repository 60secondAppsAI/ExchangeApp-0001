package com.ExchangeApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.ExchangeApp.dao.GenericDAO;
import com.ExchangeApp.service.GenericService;
import com.ExchangeApp.service.impl.GenericServiceImpl;
import com.ExchangeApp.dao.ExchangeInstrumentDAO;
import com.ExchangeApp.domain.ExchangeInstrument;
import com.ExchangeApp.dto.ExchangeInstrumentDTO;
import com.ExchangeApp.dto.ExchangeInstrumentSearchDTO;
import com.ExchangeApp.dto.ExchangeInstrumentPageDTO;
import com.ExchangeApp.dto.ExchangeInstrumentConvertCriteriaDTO;
import com.ExchangeApp.dto.common.RequestDTO;
import com.ExchangeApp.dto.common.ResultDTO;
import com.ExchangeApp.service.ExchangeInstrumentService;
import com.ExchangeApp.util.ControllerUtils;


@Service
public class ExchangeInstrumentServiceImpl extends GenericServiceImpl<ExchangeInstrument, Integer> implements ExchangeInstrumentService {

    private final static Logger logger = LoggerFactory.getLogger(ExchangeInstrumentServiceImpl.class);

	@Autowired
	ExchangeInstrumentDAO exchangeInstrumentDao;

	

	@Override
	public GenericDAO<ExchangeInstrument, Integer> getDAO() {
		return (GenericDAO<ExchangeInstrument, Integer>) exchangeInstrumentDao;
	}
	
	public List<ExchangeInstrument> findAll () {
		List<ExchangeInstrument> exchangeInstruments = exchangeInstrumentDao.findAll();
		
		return exchangeInstruments;	
		
	}

	public ResultDTO addExchangeInstrument(ExchangeInstrumentDTO exchangeInstrumentDTO, RequestDTO requestDTO) {

		ExchangeInstrument exchangeInstrument = new ExchangeInstrument();

		exchangeInstrument.setExchangeInstrumentId(exchangeInstrumentDTO.getExchangeInstrumentId());

		exchangeInstrument.setExchangeId(exchangeInstrumentDTO.getExchangeId());

		exchangeInstrument.setInstrumentId(exchangeInstrumentDTO.getInstrumentId());

		exchangeInstrument.setExchangeSymbol(exchangeInstrumentDTO.getExchangeSymbol());

		exchangeInstrument.setActive(exchangeInstrumentDTO.getActive());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		exchangeInstrument = exchangeInstrumentDao.save(exchangeInstrument);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ExchangeInstrument> getAllExchangeInstruments(Pageable pageable) {
		return exchangeInstrumentDao.findAll(pageable);
	}

	public Page<ExchangeInstrument> getAllExchangeInstruments(Specification<ExchangeInstrument> spec, Pageable pageable) {
		return exchangeInstrumentDao.findAll(spec, pageable);
	}

	public ResponseEntity<ExchangeInstrumentPageDTO> getExchangeInstruments(ExchangeInstrumentSearchDTO exchangeInstrumentSearchDTO) {
	
			Integer exchangeInstrumentId = exchangeInstrumentSearchDTO.getExchangeInstrumentId(); 
 			Integer exchangeId = exchangeInstrumentSearchDTO.getExchangeId(); 
 			Integer instrumentId = exchangeInstrumentSearchDTO.getInstrumentId(); 
 			String exchangeSymbol = exchangeInstrumentSearchDTO.getExchangeSymbol(); 
  			String sortBy = exchangeInstrumentSearchDTO.getSortBy();
			String sortOrder = exchangeInstrumentSearchDTO.getSortOrder();
			String searchQuery = exchangeInstrumentSearchDTO.getSearchQuery();
			Integer page = exchangeInstrumentSearchDTO.getPage();
			Integer size = exchangeInstrumentSearchDTO.getSize();

	        Specification<ExchangeInstrument> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, exchangeInstrumentId, "exchangeInstrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, exchangeId, "exchangeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, exchangeSymbol, "exchangeSymbol"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("exchangeSymbol")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<ExchangeInstrument> exchangeInstruments = this.getAllExchangeInstruments(spec, pageable);
		
		//System.out.println(String.valueOf(exchangeInstruments.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(exchangeInstruments.getTotalPages()));
		
		List<ExchangeInstrument> exchangeInstrumentsList = exchangeInstruments.getContent();
		
		ExchangeInstrumentConvertCriteriaDTO convertCriteria = new ExchangeInstrumentConvertCriteriaDTO();
		List<ExchangeInstrumentDTO> exchangeInstrumentDTOs = this.convertExchangeInstrumentsToExchangeInstrumentDTOs(exchangeInstrumentsList,convertCriteria);
		
		ExchangeInstrumentPageDTO exchangeInstrumentPageDTO = new ExchangeInstrumentPageDTO();
		exchangeInstrumentPageDTO.setExchangeInstruments(exchangeInstrumentDTOs);
		exchangeInstrumentPageDTO.setTotalElements(exchangeInstruments.getTotalElements());
		return ResponseEntity.ok(exchangeInstrumentPageDTO);
	}

	public List<ExchangeInstrumentDTO> convertExchangeInstrumentsToExchangeInstrumentDTOs(List<ExchangeInstrument> exchangeInstruments, ExchangeInstrumentConvertCriteriaDTO convertCriteria) {
		
		List<ExchangeInstrumentDTO> exchangeInstrumentDTOs = new ArrayList<ExchangeInstrumentDTO>();
		
		for (ExchangeInstrument exchangeInstrument : exchangeInstruments) {
			exchangeInstrumentDTOs.add(convertExchangeInstrumentToExchangeInstrumentDTO(exchangeInstrument,convertCriteria));
		}
		
		return exchangeInstrumentDTOs;

	}
	
	public ExchangeInstrumentDTO convertExchangeInstrumentToExchangeInstrumentDTO(ExchangeInstrument exchangeInstrument, ExchangeInstrumentConvertCriteriaDTO convertCriteria) {
		
		ExchangeInstrumentDTO exchangeInstrumentDTO = new ExchangeInstrumentDTO();

		exchangeInstrumentDTO.setExchangeInstrumentId(exchangeInstrument.getExchangeInstrumentId());

		exchangeInstrumentDTO.setExchangeId(exchangeInstrument.getExchangeId());

		exchangeInstrumentDTO.setInstrumentId(exchangeInstrument.getInstrumentId());

		exchangeInstrumentDTO.setExchangeSymbol(exchangeInstrument.getExchangeSymbol());

		exchangeInstrumentDTO.setActive(exchangeInstrument.getActive());
		
		return exchangeInstrumentDTO;
	}

	public ResultDTO updateExchangeInstrument(ExchangeInstrumentDTO exchangeInstrumentDTO, RequestDTO requestDTO) {
		
		ExchangeInstrument exchangeInstrument = exchangeInstrumentDao.getById(exchangeInstrumentDTO.getExchangeInstrumentId());
		
		exchangeInstrument.setExchangeInstrumentId(ControllerUtils.setValue(exchangeInstrument.getExchangeInstrumentId(), exchangeInstrumentDTO.getExchangeInstrumentId()));
		
		exchangeInstrument.setExchangeId(ControllerUtils.setValue(exchangeInstrument.getExchangeId(), exchangeInstrumentDTO.getExchangeId()));
		
		exchangeInstrument.setInstrumentId(ControllerUtils.setValue(exchangeInstrument.getInstrumentId(), exchangeInstrumentDTO.getInstrumentId()));
		
		exchangeInstrument.setExchangeSymbol(ControllerUtils.setValue(exchangeInstrument.getExchangeSymbol(), exchangeInstrumentDTO.getExchangeSymbol()));
		
		exchangeInstrument.setActive(ControllerUtils.setValue(exchangeInstrument.getActive(), exchangeInstrumentDTO.getActive()));

        exchangeInstrument = exchangeInstrumentDao.save(exchangeInstrument);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ExchangeInstrumentDTO getExchangeInstrumentDTOById(Integer exchangeInstrumentId) {
	
		ExchangeInstrument exchangeInstrument = exchangeInstrumentDao.getById(exchangeInstrumentId);
		
		ExchangeInstrumentConvertCriteriaDTO convertCriteria = new ExchangeInstrumentConvertCriteriaDTO();
		return(this.convertExchangeInstrumentToExchangeInstrumentDTO(exchangeInstrument,convertCriteria));
	}

}
