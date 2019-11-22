package com.ibm.microservices.currencyexchangeservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExchangeNotFoundException extends Exception {
	

	private static final long serialVersionUID = 1L;

	public ExchangeNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
