package com.ExchangeApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TradingSessionSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer tradingSessionId;
	
	private Integer exchangeId;
	
	private Date sessionDate;
	
	private String openTime;
	
	private String closeTime;
	
	private String status;
	
}
