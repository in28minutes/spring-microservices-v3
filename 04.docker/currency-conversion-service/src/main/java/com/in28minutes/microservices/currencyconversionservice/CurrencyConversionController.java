package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.web.client.RestTemplateBuilder; // Enable for Spring Boot 3.0.x
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.ResponseEntity; // Enable for Spring Boot 3.0.x
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient; // Enable for Spring Boot 4.0.x
// import org.springframework.web.client.RestTemplate; // Enable for Spring Boot 3.0.x

// Enable for Spring Boot 3.0.x
//@Configuration(proxyBeanMethods = false)
//class RestTemplateConfiguration {
//
//    @Bean
//    RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//}

@Configuration(proxyBeanMethods = false)
class RestClientConfiguration {

    @Bean
	RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;

    // Uncomment for Spring Boot 3.0.x
//	@Autowired
//	private RestTemplate restTemplate;

    @Autowired
    private RestClient restClient;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from",from);
		uriVariables.put("to",to);

        //		ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity
//		("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
//				CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = restClient.get()
                .uri("http://localhost:8000/currency-exchange/from/{from}/to/{to}", uriVariables)
                .retrieve()
                .body(CurrencyConversion.class);

        // CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion(currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()+ " " + "rest client");
		
	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
				
		CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to, quantity, 
				currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment() + " " + "feign");
		
	}


}
