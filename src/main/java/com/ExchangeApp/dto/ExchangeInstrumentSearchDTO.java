package com.ExchangeApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExchangeInstrumentSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer exchangeInstrumentId;
	
	private Integer exchangeId;
	
	private Integer instrumentId;
	
	private String exchangeSymbol;
	
	private Boolean active;
	
}
