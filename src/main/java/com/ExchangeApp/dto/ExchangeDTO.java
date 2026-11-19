package com.ExchangeApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ExchangeDTO {

	private Integer exchangeId;

	private String name;

	private String code;

	private String country;

	private String timezone;

	private String status;






}
