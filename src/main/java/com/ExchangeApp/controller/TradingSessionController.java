package com.ExchangeApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.ExchangeApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.ExchangeApp.domain.TradingSession;
import com.ExchangeApp.dto.TradingSessionDTO;
import com.ExchangeApp.dto.TradingSessionSearchDTO;
import com.ExchangeApp.dto.TradingSessionPageDTO;
import com.ExchangeApp.service.TradingSessionService;
import com.ExchangeApp.dto.common.RequestDTO;
import com.ExchangeApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/tradingSession")
@RestController
public class TradingSessionController {

	private final static Logger logger = LoggerFactory.getLogger(TradingSessionController.class);

	@Autowired
	TradingSessionService tradingSessionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<TradingSession> getAll() {

		List<TradingSession> tradingSessions = tradingSessionService.findAll();
		
		return tradingSessions;	
	}

	@GetMapping(value = "/{tradingSessionId}")
	@ResponseBody
	public TradingSessionDTO getTradingSession(@PathVariable Integer tradingSessionId) {
		
		return (tradingSessionService.getTradingSessionDTOById(tradingSessionId));
	}

 	@RequestMapping(value = "/addTradingSession", method = RequestMethod.POST)
	public ResponseEntity<?> addTradingSession(@RequestBody TradingSessionDTO tradingSessionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradingSessionService.addTradingSession(tradingSessionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/tradingSessions")
	public ResponseEntity<TradingSessionPageDTO> getTradingSessions(TradingSessionSearchDTO tradingSessionSearchDTO) {
 
		return tradingSessionService.getTradingSessions(tradingSessionSearchDTO);
	}	

	@RequestMapping(value = "/updateTradingSession", method = RequestMethod.POST)
	public ResponseEntity<?> updateTradingSession(@RequestBody TradingSessionDTO tradingSessionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = tradingSessionService.updateTradingSession(tradingSessionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
