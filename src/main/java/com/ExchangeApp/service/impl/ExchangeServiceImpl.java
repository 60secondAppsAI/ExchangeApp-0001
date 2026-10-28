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
import com.ExchangeApp.dao.ExchangeDAO;
import com.ExchangeApp.domain.Exchange;
import com.ExchangeApp.dto.ExchangeDTO;
import com.ExchangeApp.dto.ExchangeSearchDTO;
import com.ExchangeApp.dto.ExchangePageDTO;
import com.ExchangeApp.dto.ExchangeConvertCriteriaDTO;
import com.ExchangeApp.dto.common.RequestDTO;
import com.ExchangeApp.dto.common.ResultDTO;
import com.ExchangeApp.service.ExchangeService;
import com.ExchangeApp.util.ControllerUtils;


@Service
public class ExchangeServiceImpl extends GenericServiceImpl<Exchange, Integer> implements ExchangeService {

    private final static Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

	@Autowired
	ExchangeDAO exchangeDao;

	

	@Override
	public GenericDAO<Exchange, Integer> getDAO() {
		return (GenericDAO<Exchange, Integer>) exchangeDao;
	}
	
	public List<Exchange> findAll () {
		List<Exchange> exchanges = exchangeDao.findAll();
		
		return exchanges;	
		
	}

	public ResultDTO addExchange(ExchangeDTO exchangeDTO, RequestDTO requestDTO) {

		Exchange exchange = new Exchange();

		exchange.setExchangeId(exchangeDTO.getExchangeId());

		exchange.setName(exchangeDTO.getName());

		exchange.setCode(exchangeDTO.getCode());

		exchange.setCountry(exchangeDTO.getCountry());

		exchange.setTimezone(exchangeDTO.getTimezone());

		exchange.setStatus(exchangeDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		exchange = exchangeDao.save(exchange);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Exchange> getAllExchanges(Pageable pageable) {
		return exchangeDao.findAll(pageable);
	}

	public Page<Exchange> getAllExchanges(Specification<Exchange> spec, Pageable pageable) {
		return exchangeDao.findAll(spec, pageable);
	}

	public ResponseEntity<ExchangePageDTO> getExchanges(ExchangeSearchDTO exchangeSearchDTO) {
	
			Integer exchangeId = exchangeSearchDTO.getExchangeId(); 
 			String name = exchangeSearchDTO.getName(); 
 			String code = exchangeSearchDTO.getCode(); 
 			String country = exchangeSearchDTO.getCountry(); 
 			String timezone = exchangeSearchDTO.getTimezone(); 
 			String status = exchangeSearchDTO.getStatus(); 
 			String sortBy = exchangeSearchDTO.getSortBy();
			String sortOrder = exchangeSearchDTO.getSortOrder();
			String searchQuery = exchangeSearchDTO.getSearchQuery();
			Integer page = exchangeSearchDTO.getPage();
			Integer size = exchangeSearchDTO.getSize();

	        Specification<Exchange> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, exchangeId, "exchangeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, code, "code"); 
			
			spec = ControllerUtils.andIfNecessary(spec, country, "country"); 
			
			spec = ControllerUtils.andIfNecessary(spec, timezone, "timezone"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("code")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("country")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("timezone")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Exchange> exchanges = this.getAllExchanges(spec, pageable);
		
		//System.out.println(String.valueOf(exchanges.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(exchanges.getTotalPages()));
		
		List<Exchange> exchangesList = exchanges.getContent();
		
		ExchangeConvertCriteriaDTO convertCriteria = new ExchangeConvertCriteriaDTO();
		List<ExchangeDTO> exchangeDTOs = this.convertExchangesToExchangeDTOs(exchangesList,convertCriteria);
		
		ExchangePageDTO exchangePageDTO = new ExchangePageDTO();
		exchangePageDTO.setExchanges(exchangeDTOs);
		exchangePageDTO.setTotalElements(exchanges.getTotalElements());
		return ResponseEntity.ok(exchangePageDTO);
	}

	public List<ExchangeDTO> convertExchangesToExchangeDTOs(List<Exchange> exchanges, ExchangeConvertCriteriaDTO convertCriteria) {
		
		List<ExchangeDTO> exchangeDTOs = new ArrayList<ExchangeDTO>();
		
		for (Exchange exchange : exchanges) {
			exchangeDTOs.add(convertExchangeToExchangeDTO(exchange,convertCriteria));
		}
		
		return exchangeDTOs;

	}
	
	public ExchangeDTO convertExchangeToExchangeDTO(Exchange exchange, ExchangeConvertCriteriaDTO convertCriteria) {
		
		ExchangeDTO exchangeDTO = new ExchangeDTO();

		exchangeDTO.setExchangeId(exchange.getExchangeId());

		exchangeDTO.setName(exchange.getName());

		exchangeDTO.setCode(exchange.getCode());

		exchangeDTO.setCountry(exchange.getCountry());

		exchangeDTO.setTimezone(exchange.getTimezone());

		exchangeDTO.setStatus(exchange.getStatus());
		
		return exchangeDTO;
	}

	public ResultDTO updateExchange(ExchangeDTO exchangeDTO, RequestDTO requestDTO) {
		
		Exchange exchange = exchangeDao.getById(exchangeDTO.getExchangeId());
		
		exchange.setExchangeId(ControllerUtils.setValue(exchange.getExchangeId(), exchangeDTO.getExchangeId()));
		
		exchange.setName(ControllerUtils.setValue(exchange.getName(), exchangeDTO.getName()));
		
		exchange.setCode(ControllerUtils.setValue(exchange.getCode(), exchangeDTO.getCode()));
		
		exchange.setCountry(ControllerUtils.setValue(exchange.getCountry(), exchangeDTO.getCountry()));
		
		exchange.setTimezone(ControllerUtils.setValue(exchange.getTimezone(), exchangeDTO.getTimezone()));
		
		exchange.setStatus(ControllerUtils.setValue(exchange.getStatus(), exchangeDTO.getStatus()));

        exchange = exchangeDao.save(exchange);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ExchangeDTO getExchangeDTOById(Integer exchangeId) {
	
		Exchange exchange = exchangeDao.getById(exchangeId);
		
		ExchangeConvertCriteriaDTO convertCriteria = new ExchangeConvertCriteriaDTO();
		return(this.convertExchangeToExchangeDTO(exchange,convertCriteria));
	}

}
