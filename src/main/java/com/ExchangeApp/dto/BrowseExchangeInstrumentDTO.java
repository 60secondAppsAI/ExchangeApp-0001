package com.ExchangeApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseExchangeInstrumentDTO {

	private Integer ownerId;

	private Integer exchangeInstrumentId;

	private Integer exchangeInstrumentStatus;
	
	private Integer nextOrPrevious;
}

