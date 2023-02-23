# Video Lectures

## New

```
02 Step 12 00 - Getting Started with Observability and OpenTelemetry
```

## Updated

```
01 Step 21 - QuickStart by Importing Microservices
02 Step 12 - Connecting Currency Exchange Microservice with Zipkin
02 Step 13 - Connecting Currency Conversion Microservice _ API Gateway with Zipkin
02 Step 20 - Running Zipkin with Docker Compose
```

## Deleted 

```
Lecture - 189. Step 21 - Running Zipkin and RabbitMQ with Docker Compose
```

# Text Lectures

## RECOMMENDED: Use Latest Spring Boot Version

Thank you so much for enrolling, I'm so excited for you to start your microservices journey!

#### 👉 DO YOU KNOW?

All code in the course is updated to Spring Boot 3.

#### 👉 WHAT SHOULD YOU DO?

Bookmark the Github Repo of the course - https://github.com/in28minutes/spring-microservices-v3

I'll see you at the next lecture!

Happy Learning

Ranga

## Next Lecture - Configuration for Connecting Microservices with Zipkin

I'm delighted to have the privilege of being your instructor.

#### 👉 DO YOU KNOW?

In the next lecture, we will configure connect microservices with Zipkin.

#### 👉 WHAT SHOULD YOU DO?

Request you to bookmark this page. We will make use of it in the next lecture - https://github.com/in28minutes/spring-microservices-v3/blob/main/v3-upgrade.md


I'll see you at the next lecture!

Happy Learning

Ranga

## Course Update - Github Repositories

BEFORE 01 Step 21 - QuickStart by Importing Microservices

Thank you so much for enrolling, I'm so excited for you to start your microservices journey!

#### 👉 DO YOU KNOW?

We are maintaining multiple Github Repositories for this course to make it easy for you to use different Spring Boot versions.

#### 👉 WHAT SHOULD YOU DO?

Use the following repo's based on your Spring Boot version
- Spring Boot 3.0+ - https://github.com/in28minutes/spring-microservices-v3
- Spring Boot 2.4+ - https://github.com/in28minutes/spring-microservices-v2

I'll see you at the next lecture!

Happy Learning

Ranga


## Spring Boot 3 Update - Zipkin URL Configuration

BEFORE Step 20 - Running Zipkin with Docker Compose

I'm delighted to have the privilege of being your instructor.

#### 👉 DO YOU KNOW?

Configuration of Zipkin URL is a little different in Spring Boot 3.

#### 👉 WHAT SHOULD YOU DO?

In the docker compose configuration, please use MANAGEMENT.ZIPKIN.TRACING.ENDPOINT instead of SPRING.ZIPKIN.BASEURL. An example is shown below.

```yaml
#SPRING.ZIPKIN.BASEURL: http://zipkin-server:9411/ #SB2
MANAGEMENT.ZIPKIN.TRACING.ENDPOINT: http://zipkin-server:9411/api/v2/spans #SB3
```

Complete Docker Compose File - https://github.com/in28minutes/spring-microservices-v3/blob/main/04.docker/backup/docker-compose-05-zipkin.yaml

I'll see you at the next lecture!

Happy Learning

Ranga


# COURSE DESCRIPTION
- Remove mentions of RabbitMQ
- Update Text Lectures
    - Change Links from V2 to V3


# Announcement

## Course Upgraded - Spring Boot 3 and OpenTelemetry

Congratulations!

The course is now completely upgraded to Spring Boot 3.

#### What Has Changed

Here is the list of code changes - https://github.com/in28minutes/spring-microservices-v3/blob/main/v3-upgrade.md

#### What's New

We added in Step By Step Guides for Docker and Kubernetes sections

- Docker - https://github.com/in28minutes/spring-microservices-v3/tree/main/04.docker/01-step-by-step-changes
- Kubernetes - https://github.com/in28minutes/spring-microservices-v3/tree/main/05.kubernetes

#### Details

Following are some of the lectures which are added/modified

- Step 12 00 - Getting Started with Observability and OpenTelemetry
- Step 12 - Connecting Currency Exchange Microservice with Zipkin
- Step 13 - Connecting Currency Conversion Microservice _ API Gateway with Zipkin
- Step 20 - Running Zipkin with Docker Compose

