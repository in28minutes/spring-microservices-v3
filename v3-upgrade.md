# Microservices with Spring Boot, Spring Cloud, Docker & Kubernetes

- **V1** (Spring Boot - 2.0.0 to 2.3.x)
- **V2** (Spring Boot - 2.4.x to 3.0.0 to LATEST)

## Spring Boot 3 Upgrades

### Versions in pom.xml

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <!--<version>2.4.1</version> SB2-->
    <version>3.5.0</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<properties>

    <!-- <java.version>15</java.version> SB2-->
    <java.version>21</java.version>

    <!--<spring-cloud.version>2020.0.0</spring-cloud.version> SB2-->
    <spring-cloud.version>2024.0.1</spring-cloud.version>

</properties>
```

## Jakarta instead of javax - CurrencyExchange.java

```java
//import javax.persistence.Column; //SB2
//import javax.persistence.Entity; //SB2
//import javax.persistence.Id;  //SB2
import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
```


### Distributed Tracing

### pom.xml

```xml
<!-- SB2  : Sleuth (Tracing Configuration) 
            > Brave (Tracer library) 
            > Zipkin 
-->

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

<!-- SB3 :  Micrometer 
            > OpenTelemetry 
            > Zipkin 
-->

<!-- Micrometer - Vendor-neutral application observability facade. 
    Instrument your JVM-based application code without vendor lock-in.  
    Observation (Metrics & Logs) + Tracing.
-->

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-observation</artifactId>
</dependency>

<!-- OPTION 1: Open Telemetry as Bridge (RECOMMENDED) -->
<!-- Open Telemetry 
    - Simplified Observability (metrics, logs, and traces) -->

<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
</dependency>

<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-zipkin</artifactId>
</dependency>


<!-- OPTION 2: Brave as Bridge -->

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
  
```

### application.properties

```yaml
#spring.sleuth.sampler.probability=1.0 #SB2
management.tracing.sampling.probability=1.0 #SB3
logging.pattern.level=%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}] #SB3
```

## Currency Conversion Service - Uses Feign

pom.xml

```xml
<!-- COMMON CHANGES + -->
<!-- Enables tracing of REST API calls made using Feign - V3 ONLY-->
<dependency>
	<groupId>io.github.openfeign</groupId>
	<artifactId>feign-micrometer</artifactId>
</dependency>
``` 


### /03.microservices/currency-conversion-service/src/main/java/com/in28minutes/microservices/currencyconversionservice/CurrencyConversionController.java

```java
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

## Docker Compose Zipkin URL Configuration
```yaml
#SPRING.ZIPKIN.BASEURL: http://zipkin-server:9411/ #SB2
MANAGEMENT.ZIPKIN.TRACING.ENDPOINT: http://zipkin-server:9411/api/v2/spans #SB3
```

## Naming of Images

| Section | Image Name  | Spring Boot 2 | Spring Boot 3|
| -------- | ------------- | ------------- | ------------- |
| Docker | Currency Exchange | in28min/mmv2-currency-exchange-service:0.0.1-SNAPSHOT | in28min/mmv3-currency-exchange-service:0.0.1-SNAPSHOT|
| Docker | Currency Conversion  | in28min/mmv2-currency-conversion-service:0.0.1-SNAPSHOT  |in28min/mmv3-currency-conversion-service:0.0.1-SNAPSHOT|
| Docker | API Gateway  | in28min/mmv2-api-gateway:0.0.1-SNAPSHOT  |in28min/mmv3-api-gateway:0.0.1-SNAPSHOT|
| Docker | Naming Server | in28min/mmv2-naming-server:0.0.1-SNAPSHOT  |in28min/mmv3-naming-server:0.0.1-SNAPSHOT|
| Kubernetes | Currency Exchange | in28min/mmv2-currency-exchange-service:0.0.11-SNAPSHOT (v11)<BR/> in28min/mmv2-currency-exchange-service:0.0.12-SNAPSHOT (v12)| in28min/mmv3-currency-exchange-service:0.0.11-SNAPSHOT (v11)<BR/> in28min/mmv3-currency-exchange-service:0.0.12-SNAPSHOT (v12)|
| Kubernetes | Currency Conversion | in28min/mmv2-currency-conversion-service:0.0.11-SNAPSHOT (Uses CURRENCY_EXCHANGE_SERVICE_HOST)<BR/> in28min/mmv2-currency-conversion-service:0.0.12-SNAPSHOT (Uses CURRENCY_EXCHANGE_URI)| in28min/mmv3-currency-conversion-service:0.0.11-SNAPSHOT (Uses CURRENCY_EXCHANGE_SERVICE_HOST)<BR/> in28min/mmv3-currency-conversion-service:0.0.12-SNAPSHOT (Uses CURRENCY_EXCHANGE_URI)|
