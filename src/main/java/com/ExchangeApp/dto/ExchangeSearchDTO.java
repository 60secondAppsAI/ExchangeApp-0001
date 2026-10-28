package com.ExchangeApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExchangeSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer exchangeId;
	
	private String name;
	
	private String code;
	
	private String country;
	
	private String timezone;
	
	private String status;
	
}
