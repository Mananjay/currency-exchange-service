package com.ibm.microservices.currencyexchangeservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class ExchangeValueDAOService {

	
private static List<ExchangeValue> exchangeValues=new ArrayList<>();
	
	private static long Count=3;
	static {
		
		exchangeValues.add(new ExchangeValue(1001L,"USD","INR",BigDecimal.valueOf(65)));
		exchangeValues.add(new ExchangeValue(1002L,"EUR","INR",BigDecimal.valueOf(75)));
		exchangeValues.add(new ExchangeValue(1003L,"AUS","INR",BigDecimal.valueOf(45)));
		exchangeValues.add(new ExchangeValue(1004L,"CAD","INR",BigDecimal.valueOf(55)));
		exchangeValues.add(new ExchangeValue(1005L,"INR","USD",BigDecimal.valueOf(0.15)));
		
		
		
	}
	
	public List<ExchangeValue> findAll()
	{
		return exchangeValues;
	}
	
	public ExchangeValue save(ExchangeValue exchangeValue) {
		
		if(exchangeValue.getId()==null) {
			exchangeValue.setId(++Count);
		
		}
		
		exchangeValues.add(exchangeValue);
		return exchangeValue;
	}
	
	public ExchangeValue findOne(int id) {
		for (ExchangeValue exchangeValue : exchangeValues) {
			if(exchangeValue.getId()==id)
				return exchangeValue;
		}
		return null;
	}
	public ExchangeValue deleteById(int id) {
		Iterator<ExchangeValue> iterator=exchangeValues.iterator();
		while(iterator.hasNext()) {
			ExchangeValue exchangeValue=iterator.next();
			if(exchangeValue.getId()==id) {
				iterator.remove();
				return exchangeValue;
			}	
		}
				
	return null;
	}
	
	
	public void update(Long id, ExchangeValue exchangeValue) {

		for (ExchangeValue exchangeVal : exchangeValues) {
		/*
		if(exchangeVal.getId()!=null) {
			
			exchangeVal.setId(exchangeValue.getId());
			
	     }*/
			
		if(exchangeVal.getConversionMultiple()!=null) {
			
			exchangeVal.setConversionMultiple(exchangeValue.getConversionMultiple());
			
	    }
		if(exchangeVal.getFrom()!=null) {
	
	      exchangeVal.setFrom(exchangeValue.getFrom());
	
		}
		if(exchangeValue.getTo()!=null) {
	
		  exchangeVal.setTo(exchangeValue.getTo());
	
		 }
		/*
		if(exchangeVal.getPort()()!=null) {
			
			  exchangeVal.setPort(exchangeValue.getPort());
		
			 }
			 */
		
		}
	}	
}
