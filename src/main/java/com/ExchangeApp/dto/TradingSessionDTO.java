package com.ExchangeApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class TradingSessionDTO {

	private Integer tradingSessionId;

	private Integer exchangeId;

	private Date sessionDate;

	private String openTime;

	private String closeTime;

	private String status;






}
