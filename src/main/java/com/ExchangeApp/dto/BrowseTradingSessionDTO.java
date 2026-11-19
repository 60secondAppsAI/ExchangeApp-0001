package com.ExchangeApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTradingSessionDTO {

	private Integer ownerId;

	private Integer tradingSessionId;

	private Integer tradingSessionStatus;
	
	private Integer nextOrPrevious;
}

