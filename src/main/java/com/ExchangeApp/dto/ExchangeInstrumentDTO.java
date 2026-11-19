package com.ExchangeApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ExchangeInstrumentDTO {

	private Integer exchangeInstrumentId;

	private Integer exchangeId;

	private Integer instrumentId;

	private String exchangeSymbol;

	private Boolean active;






}
