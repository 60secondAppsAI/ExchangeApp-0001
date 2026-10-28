package com.ExchangeApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ExchangeApp.domain.ExchangeInstrument;
import com.ExchangeApp.dto.ExchangeInstrumentDTO;
import com.ExchangeApp.dto.ExchangeInstrumentSearchDTO;
import com.ExchangeApp.dto.ExchangeInstrumentPageDTO;
import com.ExchangeApp.dto.ExchangeInstrumentConvertCriteriaDTO;
import com.ExchangeApp.service.GenericService;
import com.ExchangeApp.dto.common.RequestDTO;
import com.ExchangeApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ExchangeInstrumentService extends GenericService<ExchangeInstrument, Integer> {

	List<ExchangeInstrument> findAll();

	ResultDTO addExchangeInstrument(ExchangeInstrumentDTO exchangeInstrumentDTO, RequestDTO requestDTO);

	ResultDTO updateExchangeInstrument(ExchangeInstrumentDTO exchangeInstrumentDTO, RequestDTO requestDTO);

    Page<ExchangeInstrument> getAllExchangeInstruments(Pageable pageable);

    Page<ExchangeInstrument> getAllExchangeInstruments(Specification<ExchangeInstrument> spec, Pageable pageable);

	ResponseEntity<ExchangeInstrumentPageDTO> getExchangeInstruments(ExchangeInstrumentSearchDTO exchangeInstrumentSearchDTO);
	
	List<ExchangeInstrumentDTO> convertExchangeInstrumentsToExchangeInstrumentDTOs(List<ExchangeInstrument> exchangeInstruments, ExchangeInstrumentConvertCriteriaDTO convertCriteria);

	ExchangeInstrumentDTO getExchangeInstrumentDTOById(Integer exchangeInstrumentId);



	
}
