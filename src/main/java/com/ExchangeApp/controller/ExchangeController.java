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

import com.ExchangeApp.domain.Exchange;
import com.ExchangeApp.dto.ExchangeDTO;
import com.ExchangeApp.dto.ExchangeSearchDTO;
import com.ExchangeApp.dto.ExchangePageDTO;
import com.ExchangeApp.service.ExchangeService;
import com.ExchangeApp.dto.common.RequestDTO;
import com.ExchangeApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/exchange")
@RestController
public class ExchangeController {

	private final static Logger logger = LoggerFactory.getLogger(ExchangeController.class);

	@Autowired
	ExchangeService exchangeService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Exchange> getAll() {

		List<Exchange> exchanges = exchangeService.findAll();
		
		return exchanges;	
	}

	@GetMapping(value = "/{exchangeId}")
	@ResponseBody
	public ExchangeDTO getExchange(@PathVariable Integer exchangeId) {
		
		return (exchangeService.getExchangeDTOById(exchangeId));
	}

 	@RequestMapping(value = "/addExchange", method = RequestMethod.POST)
	public ResponseEntity<?> addExchange(@RequestBody ExchangeDTO exchangeDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = exchangeService.addExchange(exchangeDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/exchanges")
	public ResponseEntity<ExchangePageDTO> getExchanges(ExchangeSearchDTO exchangeSearchDTO) {
 
		return exchangeService.getExchanges(exchangeSearchDTO);
	}	

	@RequestMapping(value = "/updateExchange", method = RequestMethod.POST)
	public ResponseEntity<?> updateExchange(@RequestBody ExchangeDTO exchangeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = exchangeService.updateExchange(exchangeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
