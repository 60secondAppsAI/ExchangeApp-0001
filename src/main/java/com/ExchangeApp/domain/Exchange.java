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
@Table(name="exchanges")
@Getter @Setter @NoArgsConstructor
public class Exchange {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="exchange_id")
	private Integer exchangeId;
    
  	@Column(name="name")
	private String name;
    
  	@Column(name="code")
	private String code;
    
  	@Column(name="country")
	private String country;
    
  	@Column(name="timezone")
	private String timezone;
    
  	@Column(name="status")
	private String status;
    

}
