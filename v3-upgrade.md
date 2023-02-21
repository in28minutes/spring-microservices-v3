## Spring Boot 3 Upgrade

## Zipkin Tracing Updates

### pom.xml

```
 	<parent>
 		<groupId>org.springframework.boot</groupId>
 		<artifactId>spring-boot-starter-parent</artifactId>
        <!--<version>2.4.1</version>-->
        <version>3.0.2</version>
 		<relativePath/> <!-- lookup parent from repository -->
 	</parent>
 
 	<properties>
        <!-- <java.version>15</java.version> -->
        <java.version>17</java.version>

        <!--<spring-cloud.version>2020.0.0</spring-cloud.version>-->
        <spring-cloud.version>2022.0.0</spring-cloud.version>
 	</properties>

    <!-- Spring Boot 2 Tracing -->

    <!-- Sleuth (Tracing Configuration) > Brave (Tracer library) > Zipkin -->

    <!-- 
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-sleuth</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-sleuth-brave</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-sleuth-zipkin</artifactId>
    </dependency>

    -->

    <!-- Spring Boot 3+ Tracing -->

    <!-- Micrometer > Brave/OpenTelemetry > Zipkin -->

    <!-- Micrometer - Vendor-neutral application observability facade. Instrument your JVM-based application code without vendor lock-in.  Observation (Metrics & Logs) + Tracing.-->

    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-observation</artifactId>
    </dependency>

    <!-- Brave as Bridge -->

    <!--
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-tracing-bridge-brave</artifactId>
    </dependency>

    <dependency>
        <groupId>io.zipkin.reporter2</groupId
        <artifactId>zipkin-reporter-brave</artifactId>
    </dependency>
    -->

    <!-- Open Telemetry as Bridge -->
    <!-- Open Telemetry - Simplified Observability (metrics, logs, and traces) -->

    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-tracing-bridge-otel</artifactId>
    </dependency>

    <dependency>
        <groupId>io.opentelemetry</groupId>
        <artifactId>opentelemetry-exporter-zipkin</artifactId>
    </dependency>
  
```

### application.properties

```
#spring.sleuth.sampler.probability=1.0
management.tracing.sampling.probability=1.0
logging.pattern.level=%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]
```

## Currency Conversion Service - Uses Feign

pom.xml

```
        <!-- COMMON CHANGES + -->
 		<!-- Enables tracing of REST API calls made using Feign-->
 		<dependency>
 			<groupId>io.github.openfeign</groupId>
 			<artifactId>feign-micrometer</artifactId>
 		</dependency>
``` 


### /03.microservices/currency-conversion-service/src/main/java/com/in28minutes/microservices/currencyconversionservice/CurrencyConversionController.java

```
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {
    
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
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
 		uriVariables.put("from",from);
 		uriVariables.put("to",to);
 		
 		//ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity
 		ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity
 		("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
 				CurrencyConversion.class, uriVariables);
``` 		



## /03.microservices/currency-exchange-service/src/main/java/com/in28minutes/microservices/currencyexchangeservice/CurrencyExchange.java

``` 
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
```


## Docker Compose Zipkin URL Configuration
```yaml
#SPRING.ZIPKIN.BASEURL: http://zipkin-server:9411/
MANAGEMENT.ZIPKIN.TRACING.ENDPOINT: http://zipkin-server:9411/api/v2/spans
```

## Naming of Images

- mmv3 instead of mmv2

- in28min/mmv3-currency-exchange-service:0.0.1-SNAPSHOT
- in28min/mmv3-currency-conversion-service:0.0.1-SNAPSHOT
- in28min/mmv3-api-gateway:0.0.1-SNAPSHOT
- in28min/mmv3-naming-server:0.0.1-SNAPSHOT
