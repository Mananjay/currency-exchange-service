package com.ibm.microservices.currencyexchangeservice;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@Autowired
	private ExchangeValueDAOService service;
	
	
	
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to){
	//Static conversion	
	//ExchangeValue exchangeValue= new ExchangeValue(1000L,from,to,BigDecimal.valueOf(65));
	
	//Dynamic Converison
	ExchangeValue exchangeValue=repository.findByFromAndTo(from, to);
	
	exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
	return exchangeValue;
		
	}
	
	@PostMapping("currency-exchange/")
	public ResponseEntity<Object> createExchangeValue(@Valid @RequestBody ExchangeValue exchangeValue){
	
		ExchangeValue savedexchangeValue=repository.save(exchangeValue);
		
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedexchangeValue.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	
	//repository.save(exchangeValue);
	
	//exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
	//return exchangeValue;
		
	}
	
	@GetMapping("currency-exchange")
	public List<ExchangeValue> findall(){
	
	return repository.findAll();
		
	}
	
	@DeleteMapping("/currency-exchange/{id}")
	public void deleteExchangeValues(@PathVariable long id) throws ExchangeNotFoundException{
		repository.deleteById(id);
	}
	
	@PutMapping("/currency-exchange/{id}")
	public void updateExchangeValue(@PathVariable Long id, @Valid @RequestBody ExchangeValue exchangeValue) {

		 Optional<ExchangeValue> exchangeVal=repository.findById(id);
		
		 ExchangeValue exchangeValueDB=null;
		 
		if(exchangeVal.isPresent()) {
			
			exchangeValueDB=exchangeVal.get();
			
			if(exchangeValue.getId()!=null) {
				
				exchangeValueDB.setId(exchangeValue.getId());
	
				}
			if(exchangeValue.getConversionMultiple()!=null) {
	
				exchangeValueDB.setConversionMultiple(exchangeValue.getConversionMultiple());
			
			}
			if(exchangeValue.getFrom()!=null) {
	
				exchangeValueDB.setFrom(exchangeValue.getFrom());
	
				}
			if(exchangeValue.getTo()!=null) {
	
				exchangeValueDB.setTo(exchangeValue.getTo());
				}
			repository.save(exchangeValueDB);
			}
		}	
	
	
	//Retrieve all the details of currency exchange
	@GetMapping("/currency-exchange-all")
	public List<ExchangeValue> retrieveExchangeAllValues(){
		
		return service.findAll();
		
	}
	
	@GetMapping("/currency-exchange-all/{id}")
	public ExchangeValue retrieveExchangeValue(@PathVariable int id) throws ExchangeNotFoundException{
		
		ExchangeValue exchangeValue= service.findOne(id);
		if (exchangeValue==null) {
			throw new ExchangeNotFoundException("id -" +id);
			
		}
		return exchangeValue;
	}
	
	@PostMapping("/currency-exchange-all")
	public ResponseEntity<Object> CreateExchangeValue(@Valid @RequestBody ExchangeValue exchangeValue){
		
		ExchangeValue savedexchangeValue=service.save(exchangeValue);
		
		URI location=ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedexchangeValue.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@DeleteMapping("/currency-exchange-all/{id}")
	public void deleteExchangeValue(@PathVariable int id) throws ExchangeNotFoundException{
		
		ExchangeValue exchangeValue= service.deleteById(id);
		if (exchangeValue==null) {
			throw new ExchangeNotFoundException("id -" +id);
			
		}
	}

	
	@PutMapping("/currency-exchange-all/{id}")
	public void updateExchangeValue(@RequestBody ExchangeValue exchangeValue, @PathVariable(required = true) Long id) {
		service.update(id, exchangeValue);
	}

	//Hystrix
	/*
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	@HystrixCommand(fallbackMethod="fallbackretrieveExchangeValues")
	public ExchangeValue retrieveExchangeValues(@PathVariable String from, @PathVariable String to){
	
		throw new RuntimeException("Not Available");
		
	}
	
	public ExchangeValue fallbackretrieveExchangeValues(@PathVariable String from, @PathVariable String to){
		
		//Static conversion	
		ExchangeValue exchangeValue= new ExchangeValue(1000L,from,to,BigDecimal.valueOf(65));
		
		return exchangeValue;
		
	}
	
*/


}
