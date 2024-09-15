package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/bearer-tokens.html#_resttemplate_support
@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.additionalInterceptors((request, body, execution) -> {
                    // Get the authentication from the SecurityContext
                    var authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication == null) {
                        return execution.execute(request, body);
                    }

                    // Check if the credentials are an instance of AbstractOAuth2Token
                    if (!(authentication.getCredentials() instanceof AbstractOAuth2Token token)) {
                        return execution.execute(request, body);
                    }

                    // Extract the token and set it in the request header
                    request.getHeaders().setBearerAuth(token.getTokenValue());

                    return execution.execute(request, body);
                })
                .build();
    }
}

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity
                ("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class, uriVariables);

        var currencyConversion = responseEntity.getBody();

        return new CurrencyConversion(currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " " + "rest template");

    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        var currencyConversion = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " " + "feign");

    }


}
