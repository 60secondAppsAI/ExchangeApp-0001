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

import com.ExchangeApp.domain.ExchangeInstrument;
import com.ExchangeApp.dto.ExchangeInstrumentDTO;
import com.ExchangeApp.dto.ExchangeInstrumentSearchDTO;
import com.ExchangeApp.dto.ExchangeInstrumentPageDTO;
import com.ExchangeApp.service.ExchangeInstrumentService;
import com.ExchangeApp.dto.common.RequestDTO;
import com.ExchangeApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/exchangeInstrument")
@RestController
public class ExchangeInstrumentController {

	private final static Logger logger = LoggerFactory.getLogger(ExchangeInstrumentController.class);

	@Autowired
	ExchangeInstrumentService exchangeInstrumentService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ExchangeInstrument> getAll() {

		List<ExchangeInstrument> exchangeInstruments = exchangeInstrumentService.findAll();
		
		return exchangeInstruments;	
	}

	@GetMapping(value = "/{exchangeInstrumentId}")
	@ResponseBody
	public ExchangeInstrumentDTO getExchangeInstrument(@PathVariable Integer exchangeInstrumentId) {
		
		return (exchangeInstrumentService.getExchangeInstrumentDTOById(exchangeInstrumentId));
	}

 	@RequestMapping(value = "/addExchangeInstrument", method = RequestMethod.POST)
	public ResponseEntity<?> addExchangeInstrument(@RequestBody ExchangeInstrumentDTO exchangeInstrumentDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = exchangeInstrumentService.addExchangeInstrument(exchangeInstrumentDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/exchangeInstruments")
	public ResponseEntity<ExchangeInstrumentPageDTO> getExchangeInstruments(ExchangeInstrumentSearchDTO exchangeInstrumentSearchDTO) {
 
		return exchangeInstrumentService.getExchangeInstruments(exchangeInstrumentSearchDTO);
	}	

	@RequestMapping(value = "/updateExchangeInstrument", method = RequestMethod.POST)
	public ResponseEntity<?> updateExchangeInstrument(@RequestBody ExchangeInstrumentDTO exchangeInstrumentDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = exchangeInstrumentService.updateExchangeInstrument(exchangeInstrumentDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
