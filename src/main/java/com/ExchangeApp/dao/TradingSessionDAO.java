package com.ExchangeApp.dao;

import java.util.List;
import java.util.Date;

import com.ExchangeApp.dao.GenericDAO;
import com.ExchangeApp.domain.TradingSession;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface TradingSessionDAO extends GenericDAO<TradingSession, Integer> {
  
	List<TradingSession> findAll();
	


}

