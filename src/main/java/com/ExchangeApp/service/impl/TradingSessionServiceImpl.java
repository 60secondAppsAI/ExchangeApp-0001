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
import com.ExchangeApp.dao.TradingSessionDAO;
import com.ExchangeApp.domain.TradingSession;
import com.ExchangeApp.dto.TradingSessionDTO;
import com.ExchangeApp.dto.TradingSessionSearchDTO;
import com.ExchangeApp.dto.TradingSessionPageDTO;
import com.ExchangeApp.dto.TradingSessionConvertCriteriaDTO;
import com.ExchangeApp.dto.common.RequestDTO;
import com.ExchangeApp.dto.common.ResultDTO;
import com.ExchangeApp.service.TradingSessionService;
import com.ExchangeApp.util.ControllerUtils;


@Service
public class TradingSessionServiceImpl extends GenericServiceImpl<TradingSession, Integer> implements TradingSessionService {

    private final static Logger logger = LoggerFactory.getLogger(TradingSessionServiceImpl.class);

	@Autowired
	TradingSessionDAO tradingSessionDao;

	

	@Override
	public GenericDAO<TradingSession, Integer> getDAO() {
		return (GenericDAO<TradingSession, Integer>) tradingSessionDao;
	}
	
	public List<TradingSession> findAll () {
		List<TradingSession> tradingSessions = tradingSessionDao.findAll();
		
		return tradingSessions;	
		
	}

	public ResultDTO addTradingSession(TradingSessionDTO tradingSessionDTO, RequestDTO requestDTO) {

		TradingSession tradingSession = new TradingSession();

		tradingSession.setTradingSessionId(tradingSessionDTO.getTradingSessionId());

		tradingSession.setExchangeId(tradingSessionDTO.getExchangeId());

		tradingSession.setSessionDate(tradingSessionDTO.getSessionDate());

		tradingSession.setOpenTime(tradingSessionDTO.getOpenTime());

		tradingSession.setCloseTime(tradingSessionDTO.getCloseTime());

		tradingSession.setStatus(tradingSessionDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		tradingSession = tradingSessionDao.save(tradingSession);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<TradingSession> getAllTradingSessions(Pageable pageable) {
		return tradingSessionDao.findAll(pageable);
	}

	public Page<TradingSession> getAllTradingSessions(Specification<TradingSession> spec, Pageable pageable) {
		return tradingSessionDao.findAll(spec, pageable);
	}

	public ResponseEntity<TradingSessionPageDTO> getTradingSessions(TradingSessionSearchDTO tradingSessionSearchDTO) {
	
			Integer tradingSessionId = tradingSessionSearchDTO.getTradingSessionId(); 
 			Integer exchangeId = tradingSessionSearchDTO.getExchangeId(); 
 			Date sessionDate = tradingSessionSearchDTO.getSessionDate();
 			String openTime = tradingSessionSearchDTO.getOpenTime(); 
 			String closeTime = tradingSessionSearchDTO.getCloseTime(); 
 			String status = tradingSessionSearchDTO.getStatus(); 
 			String sortBy = tradingSessionSearchDTO.getSortBy();
			String sortOrder = tradingSessionSearchDTO.getSortOrder();
			String searchQuery = tradingSessionSearchDTO.getSearchQuery();
			Integer page = tradingSessionSearchDTO.getPage();
			Integer size = tradingSessionSearchDTO.getSize();

	        Specification<TradingSession> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, tradingSessionId, "tradingSessionId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, exchangeId, "exchangeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, sessionDate, "sessionDate");
			
			spec = ControllerUtils.andIfNecessary(spec, openTime, "openTime"); 
			
			spec = ControllerUtils.andIfNecessary(spec, closeTime, "closeTime"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("openTime")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("closeTime")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<TradingSession> tradingSessions = this.getAllTradingSessions(spec, pageable);
		
		//System.out.println(String.valueOf(tradingSessions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(tradingSessions.getTotalPages()));
		
		List<TradingSession> tradingSessionsList = tradingSessions.getContent();
		
		TradingSessionConvertCriteriaDTO convertCriteria = new TradingSessionConvertCriteriaDTO();
		List<TradingSessionDTO> tradingSessionDTOs = this.convertTradingSessionsToTradingSessionDTOs(tradingSessionsList,convertCriteria);
		
		TradingSessionPageDTO tradingSessionPageDTO = new TradingSessionPageDTO();
		tradingSessionPageDTO.setTradingSessions(tradingSessionDTOs);
		tradingSessionPageDTO.setTotalElements(tradingSessions.getTotalElements());
		return ResponseEntity.ok(tradingSessionPageDTO);
	}

	public List<TradingSessionDTO> convertTradingSessionsToTradingSessionDTOs(List<TradingSession> tradingSessions, TradingSessionConvertCriteriaDTO convertCriteria) {
		
		List<TradingSessionDTO> tradingSessionDTOs = new ArrayList<TradingSessionDTO>();
		
		for (TradingSession tradingSession : tradingSessions) {
			tradingSessionDTOs.add(convertTradingSessionToTradingSessionDTO(tradingSession,convertCriteria));
		}
		
		return tradingSessionDTOs;

	}
	
	public TradingSessionDTO convertTradingSessionToTradingSessionDTO(TradingSession tradingSession, TradingSessionConvertCriteriaDTO convertCriteria) {
		
		TradingSessionDTO tradingSessionDTO = new TradingSessionDTO();

		tradingSessionDTO.setTradingSessionId(tradingSession.getTradingSessionId());

		tradingSessionDTO.setExchangeId(tradingSession.getExchangeId());

		tradingSessionDTO.setSessionDate(tradingSession.getSessionDate());

		tradingSessionDTO.setOpenTime(tradingSession.getOpenTime());

		tradingSessionDTO.setCloseTime(tradingSession.getCloseTime());

		tradingSessionDTO.setStatus(tradingSession.getStatus());
		
		return tradingSessionDTO;
	}

	public ResultDTO updateTradingSession(TradingSessionDTO tradingSessionDTO, RequestDTO requestDTO) {
		
		TradingSession tradingSession = tradingSessionDao.getById(tradingSessionDTO.getTradingSessionId());
		
		tradingSession.setTradingSessionId(ControllerUtils.setValue(tradingSession.getTradingSessionId(), tradingSessionDTO.getTradingSessionId()));
		
		tradingSession.setExchangeId(ControllerUtils.setValue(tradingSession.getExchangeId(), tradingSessionDTO.getExchangeId()));
		
		tradingSession.setSessionDate(ControllerUtils.setValue(tradingSession.getSessionDate(), tradingSessionDTO.getSessionDate()));
		
		tradingSession.setOpenTime(ControllerUtils.setValue(tradingSession.getOpenTime(), tradingSessionDTO.getOpenTime()));
		
		tradingSession.setCloseTime(ControllerUtils.setValue(tradingSession.getCloseTime(), tradingSessionDTO.getCloseTime()));
		
		tradingSession.setStatus(ControllerUtils.setValue(tradingSession.getStatus(), tradingSessionDTO.getStatus()));

        tradingSession = tradingSessionDao.save(tradingSession);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public TradingSessionDTO getTradingSessionDTOById(Integer tradingSessionId) {
	
		TradingSession tradingSession = tradingSessionDao.getById(tradingSessionId);
		
		TradingSessionConvertCriteriaDTO convertCriteria = new TradingSessionConvertCriteriaDTO();
		return(this.convertTradingSessionToTradingSessionDTO(tradingSession,convertCriteria));
	}

}
