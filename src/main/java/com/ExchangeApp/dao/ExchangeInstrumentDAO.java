package com.ExchangeApp.dao;

import java.util.List;
import java.util.Date;

import com.ExchangeApp.dao.GenericDAO;
import com.ExchangeApp.domain.ExchangeInstrument;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ExchangeInstrumentDAO extends GenericDAO<ExchangeInstrument, Integer> {
  
	List<ExchangeInstrument> findAll();
	


}

