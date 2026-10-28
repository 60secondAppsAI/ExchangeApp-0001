package com.ExchangeApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ExchangeApp.domain.Exchange;
import com.ExchangeApp.dto.ExchangeDTO;
import com.ExchangeApp.dto.ExchangeSearchDTO;
import com.ExchangeApp.dto.ExchangePageDTO;
import com.ExchangeApp.dto.ExchangeConvertCriteriaDTO;
import com.ExchangeApp.service.GenericService;
import com.ExchangeApp.dto.common.RequestDTO;
import com.ExchangeApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ExchangeService extends GenericService<Exchange, Integer> {

	List<Exchange> findAll();

	ResultDTO addExchange(ExchangeDTO exchangeDTO, RequestDTO requestDTO);

	ResultDTO updateExchange(ExchangeDTO exchangeDTO, RequestDTO requestDTO);

    Page<Exchange> getAllExchanges(Pageable pageable);

    Page<Exchange> getAllExchanges(Specification<Exchange> spec, Pageable pageable);

	ResponseEntity<ExchangePageDTO> getExchanges(ExchangeSearchDTO exchangeSearchDTO);
	
	List<ExchangeDTO> convertExchangesToExchangeDTOs(List<Exchange> exchanges, ExchangeConvertCriteriaDTO convertCriteria);

	ExchangeDTO getExchangeDTOById(Integer exchangeId);



	
}
