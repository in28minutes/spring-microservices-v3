package com.in28minutes.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, and @Setter on all non-final fields and @RequiredArgsConstructor
@NoArgsConstructor // Generates constructors that take no argument
@AllArgsConstructor // Generates constructors that take one argument for every field.
@Entity
public class CurrencyExchange {
	
	@Id
	private Long id;
	
	@Column(name = "currency_from")
	private String from;
	
	@Column(name = "currency_to")
	private String to;

	private BigDecimal conversionMultiple;
	private String environment;
}
