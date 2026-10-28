package com.ExchangeApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseExchangeDTO {

	private Integer ownerId;

	private Integer exchangeId;

	private Integer exchangeStatus;
	
	private Integer nextOrPrevious;
}

