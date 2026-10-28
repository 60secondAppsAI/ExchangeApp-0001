package com.ExchangeApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ExchangeApp.domain.TradingSession;
import com.ExchangeApp.dto.TradingSessionDTO;
import com.ExchangeApp.dto.TradingSessionSearchDTO;
import com.ExchangeApp.dto.TradingSessionPageDTO;
import com.ExchangeApp.dto.TradingSessionConvertCriteriaDTO;
import com.ExchangeApp.service.GenericService;
import com.ExchangeApp.dto.common.RequestDTO;
import com.ExchangeApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TradingSessionService extends GenericService<TradingSession, Integer> {

	List<TradingSession> findAll();

	ResultDTO addTradingSession(TradingSessionDTO tradingSessionDTO, RequestDTO requestDTO);

	ResultDTO updateTradingSession(TradingSessionDTO tradingSessionDTO, RequestDTO requestDTO);

    Page<TradingSession> getAllTradingSessions(Pageable pageable);

    Page<TradingSession> getAllTradingSessions(Specification<TradingSession> spec, Pageable pageable);

	ResponseEntity<TradingSessionPageDTO> getTradingSessions(TradingSessionSearchDTO tradingSessionSearchDTO);
	
	List<TradingSessionDTO> convertTradingSessionsToTradingSessionDTOs(List<TradingSession> tradingSessions, TradingSessionConvertCriteriaDTO convertCriteria);

	TradingSessionDTO getTradingSessionDTOById(Integer tradingSessionId);



	
}
