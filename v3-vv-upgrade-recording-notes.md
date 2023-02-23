# Microservices with Spring Cloud - V2

## Step 21 - QuickStart by Importing Microservices
- Editing ONLY
	- Add V3 URL

# Docker with Microservices using Spring Boot and Spring Cloud - V2

## Step 12 - Connecting Currency Exchange Microservice with Zipkin

Connect our currency exchange service to Zipkin.

All microservices will send traces to Zipkin over HTTP. Zipkin will store the traces in an in-memory data and providing a UI around it.

Dependencies are different based on the version of Spring Boot:
- SB2 dependencies
- SB3 dependencies

In addition you need sampling configuration. big performance impact if we sample every request. 1.0 - trace every request. trace 5% of the request => 0.05.

Launch Eureka up to avoid errors!

Look at trace and trace ID and other details.

private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
logger.info("retrieveExchangeValue called with {} to {}", from, to);

These IDs are not really useful right now because we just have one microservice. But as we go across multiple microservices, these IDs, which are assigned becomes really, really useful to trace the requests across multiple microservices.

## Step 13 - Connecting Currency Conversion Microservice & API Gateway with Zipkin

Copy dependencies

Configure sampling in application.properties

Launch manually (this is tedious - we will see how you can simplify this later)

Launch all four!

Use urls from url.txt

If you have any problem, what I recommend you to do is to wait for a little while.

It might take about five minutes to ten minutes for the entire thing to stabilize.

feign-micrometer and RestTemplate configuration!

How is that happening? Default configuration.  Zipkin URL.

Play around with Zipkin and I'll see you in the next step.

##  Step 13z - Docker & Kubernetes Sections - V2 and V3 Repos

In the previous section we played with all the different microservices, API gateway, currency conversion service, currency exchange service, naming server. We started up Zipkin as well. I'm sure you had a very, very interesting time

In the next series of videos, we want to simplify launching of microservices. We will be using Docker and Docker Compose in this section! We will be using Kubernetes in the next section.

We want to ensure that the course is compatible with Spring Boot 2 and Spring Boot 3. 
- **Spring Boot 2** - https://github.com/in28minutes/spring-microservices-v2
- **Spring Boot 3** - https://github.com/in28minutes/spring-microservices-v3

## New Slides 



## Step 20 - Running Zipkin with Docker Compose

- Editing ONLY
	- ADD overlay for MANAGEMENT.ZIPKIN.TRACING.ENDPOINT
	- Remove mentions of RabbitMQ
