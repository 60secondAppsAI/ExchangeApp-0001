package com.ExchangeApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="exchange_instruments")
@Getter @Setter @NoArgsConstructor
public class ExchangeInstrument {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="exchange_instrument_id")
	private Integer exchangeInstrumentId;
    
  	@Column(name="exchange_id")
	private Integer exchangeId;
    
  	@Column(name="instrument_id")
	private Integer instrumentId;
    
  	@Column(name="exchange_symbol")
	private String exchangeSymbol;
    
  	@Column(name="active")
	private Boolean active;
    

}
