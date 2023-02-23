# Debugging Guide - Microservices with Docker and Docker Compose

## URLs

### Step 00

-  Step 00 - Match made in Heaven - Docker and Microservices
-  Step 01 - Installing Docker - Docker
-  Step 02 - Your First Docker Usecase - Deploy a Spring Boot Application

```
docker --version
docker run in28min/todo-rest-api-h2:1.0.0.RELEASE
```

### Step 03

-  Step 03 - Important Docker Concepts - Registry, Repository, Tag, Image and Container

- URL - https://hub.docker.com/r/in28min/todo-rest-api-h2/tags
```
docker run -p 5000:5000 in28min/todo-rest-api-h2:1.0.0.RELEASE
```

### Step 04

- Step 04 - Playing with Docker Images and Containers
- Step 05 - Understanding Docker Architecture - Docker Client, Docker Engine

- URL - http://localhost:5000/hello-world
	- http://localhost:5001/hello-world

```sh
docker run -p -d 5000:5000 in28min/todo-rest-api-h2:1.0.0.RELEASE
docker logs 04e52ff9270f5810eefe1f77222852dc1461c22440d4ecd6228b5c38f09d838e
docker logs c2ba
docker logs -f c2ba
docker container ls
docker run -p -d 5001:5000 in28min/todo-rest-api-h2:1.0.0.RELEASE
docker images #one image - multiple containers
docker container ls -a #show stopped containers as well
docker container stop f708b7ee1a8b

docker run -p -d 5000:5000 in28min/todo-rest-api-h2:0.0.1-SNAPSHOT
```
### Step 06
-  Step 06 - Why is Docker Popular
	- Docker containers are easy to run
	- Cloud Neutral

### Step 07
-  Step 07 - Playing with Docker Images

```sh
docker images
docker pull mysql #gets latest
docker search mysql
docker image history in28min/hello-world-java:0.0.1.RELEASE
docker image history 100229ba687e
docker image inspect 100229ba687e
docker image remove mysql
```

### Step 08

-  Step 08 - Playing with Docker Containers

```
docker run -p -d 5000:5000 in28min/todo-rest-api-h2:0.0.1-SNAPSHOT
docker container rm 3e657ae9bd16
docker container ls -a
docker container pause 832
docker container unpause 832
docker container stop 832 #SIGTERM
docker container kill 832 #SIGKILL
docker container inspect ff521fa58db3
docker container prune

docker run -p -d 5000:5000 --restart=always in28min/todo-rest-api-h2:0.0.1-SNAPSHOT #automatically  starts after docker desktop is restarted
```

### Step 09

-  Step 09 - Playing with Docker Commands - stats, system

```
docker events #track events - launch and stop containers
docker top 9009722eac4d
docker stats 
docker stats 9009722eac4d
docker system
docker system df
docker system info
docker system prune -a
docker container run -p 5000:5000 -d -m 512m in28min/todo-rest-api-h2:0.0.1-SNAPSHOT
docker container run -p 5000:5000 -d -m 512m --cpu-quota=50000  in28min/todo-rest-api-h2:0.0.1-SNAPSHOT
docker system events
```

## Step 10
-  Step 10 - Introduction to Distributed Tracing
-  Step 11 - Launching Zipkin Container using Docker

```sh
docker run -p 9411:9411 openzipkin/zipkin:2.23
#explore openzipkin
```

## Step 12

-  Step 12 - Connecting Currency Exchange Microservice with Zipkin


### pom.xml

```xml

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


```properties
#spring.sleuth.sampler.probability=1.0
management.tracing.sampling.probability=1.0
logging.pattern.level=%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]

# IF YOU WANT TO RUN ZIPKIN ON DIFFERENT URL
##spring.zipkin.baseUrl=http://localhost:9411/

```

## Step 13

- Step 13 - Connecting Currency Conversion Microservice and API Gateway with Zipkin

Have a little bit of patience. Wait for 5-10 minutes if you do not see traces!

### Start with Same Changes As Previous Step

### Additional Changes For Spring Boot 3+

pom.xml
```
		<!-- Enables tracing of REST API calls made using Feign-->
		<dependency>
			<groupId>io.github.openfeign</groupId>
			<artifactId>feign-micrometer</artifactId>
		</dependency>
```

CurrencyConversionController.java
```
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

```


## Step 14

- Step 14 - Getting Setup with Microservices for Creating Container Images
- Step 15 - Creating Container Image for Currency Exchange Microservice

```
docker push docker.io/in28min/mmv3-currency-exchange-service:0.0.1-SNAPSHOT
docker-compose --version
docker-compose up
docker push in28min/mmv3-naming-server:0.0.1-SNAPSHOT
docker push in28min/mmv3-currency-conversion-service:0.0.1-SNAPSHOT
docker push in28min/mmv3-api-gateway:0.0.1-SNAPSHOT
watch -n 0.1 curl http://localhost:8000/sample-api
```


## Docker Compose

## Docker Section - Connect Microservices with Zipkin

(1) Compare and try with the Docker Compose Backup files here:
- (5 Docker Compose Backup Files)[https://github.com/in28minutes/spring-microservices-v3/tree/main/04.docker/backup]

(2) Try adding `restart: always` to zipkin-server in docker-compose.yaml

```
  zipkin-server:
    image: openzipkin/zipkin:2.23
    mem_limit: 300m
    ports:
      - "9411:9411"
    networks:
      - currency-network
    restart: always #Restart if there is a problem starting up
```

(3) Can you try adding EUREKA.CLIENT.FETCHREGISTRY property to all microservice where we configured EUREKA.CLIENT.SERVICEURL.DEFAULTZONE as shown below:

```
environment:
  EUREKA.CLIENT.SERVICEURL.DEFAULTZONE: http://naming-server:8761/eureka
  EUREKA.CLIENT.FETCHREGISTRY: "true"
```


## Step 16
-  Step 16 - Getting Started with Docker Compose - Currency Exchange Microservice

Docker Compose File - ../backup/docker-compose-01-ces.yaml

## Step 17
-  Step 17 - Running Eureka Naming Server with Docker Compose

Docker Compose File - ../backup/docker-compose-02-naming-server.yaml

## Step 18
-  Step 18 - Running Currency Conversion Microservice with Docker Compose

Docker Compose File - ../backup/docker-compose-03-currency-conversion.yaml

## Step 19
-  Step 19 - Running Spring Cloud API Gateway with Docker Compose

Docker Compose File - ../backup/docker-compose-04-api-gateway.yaml

## Step 20
-  Step 20 - Running Zipkin with Docker Compose

Docker Compose File - ../backup/docker-compose-05-zipkin.yaml
