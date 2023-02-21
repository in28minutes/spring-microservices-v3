# Spring Microservices - V2 - REST + Microservices + Spring Boot + Spring Cloud + Docker + Kubernetes

#### V2 of our most popular course - REST + Microservices + Spring Cloud + Docker + Kubernetes

[![Image](https://www.springboottutorial.com/images/Course-Master-Microservices-with-Spring-Boot-and-Spring-Cloud.png "Master Microservices with Spring Boot and Spring Cloud")](https://www.udemy.com/course/microservices-with-spring-boot-and-spring-cloud/)

Learn how to create awesome Microservices and RESTful web services with Spring and Spring Boot.

Updates
- Jan 2021: Docker and Kubernetes added to the course!
- Aug 2022: REST API V2 recorded with Spring Boot 3


## Launch MySQL as Docker Container

```
docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=social-media-user --env MYSQL_PASSWORD=dummypassword --env MYSQL_DATABASE=social-media-database --name mysql --publish 3306:3306 mysql:8-oracle
```

## Installing Tools

### Our Recommendations

- Use **latest version** of Java
- Use **latest version** of "Eclipse IDE for Enterprise Java Developers"
- Remember: Spring Boot 3+ works only with Java 17+

### Installing Java

- Windows - https://www.youtube.com/watch?v=I0SBRWVS0ok
- Linux - https://www.youtube.com/watch?v=mHvFpyHK97A
- Mac - https://www.youtube.com/watch?v=U3kTdMPlgsY

#### Troubleshooting

- Troubleshooting Java Installation - https://www.youtube.com/watch?v=UI_PabQ1YB0

### Installing Eclipse

- Windows - https://www.youtube.com/watch?v=toY06tsME-M
- Others - https://www.youtube.com/watch?v=XveQ9Gq41UM

#### Troubleshooting
- Configuring Java in Eclipse - https://www.youtube.com/watch?v=8i0r_fcE3L0

## What will you learn?

- Docker
- Kubernetes
- Spring Boot 2.4.x+ & Spring Cloud 2020.x+
  - Service Registry using Eureka Naming Server
  - Load Balancing with Spring Cloud LoadBalancer (replacing Ribbon)
  - API Gateway with Spring Cloud Gateway (instead of Zuul)
  - Circuit Breaker with Resilience4j (instead of Hystrix)
  - Distributed Tracing with Zipkin
  - Asynchronous Communication using Rabbit MQ

#### Microservices with Spring Cloud - V2

-  Step 01 - Setting up Limits Microservice
-  Step 02 - Creating a hard coded limits service
-  Step 03 - Enhance limits service to pick up configuration from application properties
-  Step 04 - Setting up Spring Cloud Config Server
-  Step 05 - Installing Git and Creating Local Git Repository
-  Step 06 - Connect Spring Cloud Config Server to Local Git Repository
-  Step 07 - Connect Limits Service to Spring Cloud Config Server
-  Step 08 - Configuring Profiles for Limits Service
-  Step 09 - Introduction to Currency Conversion and Currency Exchange Microservices
-  Step 10 - Setting up Currency Exchange Microservice
-  Step 11 - Create a simple hard coded currency exchange service
-  Step 12 - Setting up Dynamic Port in the the Response
-  Step 13 - Configure JPA and Initialized Data
-  Step 14 - Create a JPA Repository
-  Step 15 - Setting up Currency Conversion Microservice
-  Step 16 - Creating a service for currency conversion
-  Step 17 - Invoking Currency Exchange Microservice from Currency Conversion Microservice
-  Step 18 - Using Feign REST Client for Service Invocation
-  Step 19 - Understand Naming Server and Setting up Eureka Naming Server
-  Step 20 - Connect Currency Conversion Microservice & Currency Exchange Microservice to Eureka
-  Step 21 - Load Balancing with Eureka, Feign & Spring Cloud LoadBalancer
-  Step 22 - Setting up Spring Cloud API Gateway
-  Step 23 - Enabling Discovery Locator with Eureka for Spring Cloud Gateway
-  Step 24 - Exploring Routes with Spring Cloud Gateway
-  Step 25 - Implementing Spring Cloud Gateway Logging Filter
-  Step 26 - Getting started with Circuit Breaker - Resilience4j
-  Step 27 - Playing with Resilience4j - Retry and Fallback Methods
-  Step 28 - Playing with Circuit Breaker Features of Resilience4j
-  Step 29 - Exploring Rate Limiting and BulkHead Features of Resilience4j

#### Docker with Microservices using Spring Boot and Spring Cloud - V2

-  Step 00 - Match made in Heaven - Docker and Microservices
-  Step 01 - Installing Docker - Docker
-  Step 02 - Your First Docker Usecase - Deploy a Spring Boot Application
-  Step 03 - Important Docker Concepts - Registry, Repository, Tag, Image and Container
-  Step 04 - Playing with Docker Images and Containers
-  Step 05 - Understanding Docker Architecture - Docker Client, Docker Engine
-  Step 06 - Why is Docker Popular
-  Step 07 - Playing with Docker Images
-  Step 08 - Playing with Docker Containers
-  Step 09 - Playing with Docker Commands - stats, system
-  Step 10 - Introduction to Distributed Tracing
-  Step 11 - Launching Zipkin Container using Docker
-  Step 12 - Connecting Currency Exchange Microservice with Zipkin
-  Step 13 - Connecting Currency Conversion Microservice and API Gateway with Zipkin
-  Step 14 - Getting Setup with Microservices for Creating Container Images
-  Step 15 - Creating Container Image for Currency Exchange Microservice
-  Step 16 - Getting Started with Docker Compose - Currency Exchange Microservice
-  Step 17 - Running Eureka Naming Server with Docker Compose
-  Step 18 - Running Currency Conversion Microservice with Docker Compose
-  Step 19 - Running Spring Cloud API Gateway with Docker Compose
-  Step 20 - Running Zipkin with Docker Compose
-  Step 21 - Running Zipkin and RabbitMQ with Docker Compose

#### Kubernetes with Microservices using Docker, Spring Boot and Spring Cloud - V2

-  Step 00 - Docker, Kubernetes and Microservices - Made for each other
-  Step 01 - Getting Started with Docker, Kubernetes and Google Kubernetes Engine
-  Step 02 - Creating Google Cloud Account
-  Step 03 - Creating Kubernetes Cluster with Google Kubernete Engine (GKE)
-  Step 04 - Review Kubernetes Cluster and Learn Few Fun Facts about Kubernetes
-  Step 05 - Deploy Your First Spring Boot Application to Kubernetes Cluster
-  Step 06 - Quick Look at Kubernetes Concepts - Pods, Replica Sets and Deployment
-  Step 07 - Understanding Pods in Kubernetes
-  Step 08 - Understanding ReplicaSets in Kubernetes
-  Step 09 - Understanding Deployment in Kubernetes
-  Step 10 - Quick Review of Kubernetes Concepts - Pods, Replica Sets and Deployment
-  Step 11 - Understanding Services in Kubernetes
-  Step 12 - Quick Review of GKE on Google Cloud Console 
-  Step 13 - Understanding Kubernetes Architecture - Master Node and Nodes
-  Step 14 - Setup Currency Exchange & Currency Conversion Microservices - K8S versions
-  Step 15 - Create Container images for Currency Exchange & Currency Conversion Microservices
-  Step 16 - Deploy Microservices to Kubernetes & Understand Service Discovery
-  Step 17 - Creating Declarative Configuration Kubernetes YAML for Microservices
-  Step 18 - Clean up Kubernetes YAML for Microservices
-  Step 19 - Enable Logging and Tracing APIs in Google Cloud Platform
-  Step 20 - Deploying Microservices using Kubernetes YAML Configuration
-  Step 21 - Playing with Kubernetes Declarative YAML Configuration
-  Step 22 - Creating Environment Variables to enable Microservice Communication
-  Step 23 - Understanding Centralized Configuration in Kubernetes - Config Maps
-  Step 24 - Exploring Centralized Logging and Monitoring in GKE
-  Step 25 - Exploring Microservices Deployments with Kubernetes
-  Step 26 - Configuring Liveness and Readiness Probes for Microservices with K8S
-  Step 27 - Autoscaling Microservices with Kubernetes
-  Step 28 - Delete Kubernetes Cluster and Thank You!

## URLS

- http://localhost:8080/limits
- http://localhost:8888/limits-service/default


## Diagrams

```
digraph architecture {
  rankdir=LR;
node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"]
node[style=filled,color="#59C8DE"];

Microservice1 -> Microservice2 -> Microservice3 -> Microservice4 -> Microservice5


}

digraph architecture {
  rankdir=TB;
node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"]
node[style=filled,color="#59C8DE"];

Microservice1 -> Microservice2 -> Microservice3

{rank=same; Microservice1, A1, A2};
{rank=same; Microservice2, B1, B2, B3, B4};
{rank=same; Microservice3, C1};

}

digraph architecture {
  rankdir=TB;
node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"]
{rank=same; MovieService, CustomerService, ReviewService, BookingService, FareCalculationService};
node[style=filled,color="#59C8DE"];
DB1,DB2,DB3,DB4,DB5[shape=cylinder]

  MovieService->DB1;
  CustomerService->DB2;
ReviewService->DB3;
BookingService ->DB4;
FareCalculationService ->DB5;


}

digraph architecture {
  rankdir=TB;
rankdir=TB;
node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"]
{rank=same; MovieApplication};
LARGEDB[shape=cylinder];
  MovieApplication->LARGEDB;

}

digraph architecture {
rankdir = TB;
node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"]
//node [style=filled,color="#D14D28"]
Git[shape=cylinder]
{rank=same; LimitsService,MicroserviceX,MicroserviceY};
LimitsService -> SpringCloudConfigServer
MicroserviceX -> SpringCloudConfigServer
MicroserviceY -> SpringCloudConfigServer
SpringCloudConfigServer -> Git
LimitsService[label=<Limits Microservice>]
MicroserviceX[label=<Microservice X>]
MicroserviceY[label=<Microservice Y>]
SpringCloudConfigServer[label=<Spring Cloud Config Server>]
Git[label=<Git Repo>]

}


digraph architecture {
rankdir = TB;
node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"];
//node [style=filled,color="#D14D28"]
  CurrencyCalculationService -> CurrencyExchangeService;
  
  CurrencyExchangeService->Database;
Database[shape=cylinder]
CurrencyCalculationService[label=<Currency Conversion Microservice>];
CurrencyExchangeService[label=<Currency Exchange Microservice>];
CurrencyCalculationService, CurrencyExchangeService[shape=recordfs]
{rank=same; CurrencyCalculationService, CurrencyExchangeService};
  CurrencyCalculationService[label=<Currency Conversion Microservice>];
CurrencyExchangeService[label=<Currency Exchange Microservice>];

}


Microservices-Environments
~~~~~~~~~~~~~~~~~~~~~~~~~~~`
digraph architecture {
  rankdir=LR;

node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"];


  CurrencyCalculationService -> CurrencyExchangeService -> MicroserviceX

  subgraph CurrencyCalculationService {
      {rank=same; CurrencyCalculationService,CCDEV,CCQA,CCSTAGE, CCPROD};
  }
 
  subgraph CurrencyExchangeService {
      CurrencyExchangeService;
      {rank=same; CurrencyExchangeService,CEDEV,CEQA, CESTAGE, CEPROD};
  }
  
  subgraph MicroserviceX {
      CurrencyExchangeService;
      {rank=same; MicroserviceX,LSDEV,LSQA, LSSTAGE, LSPROD};
  }
 
  CCDEV,CEDEV,LSDEV[label=<DEV>,width=1]
  CCQA,CEQA,LSQA[label=<QA>,width=1]
  CCSTAGE,CESTAGE,LSSTAGE[label=<STAGE>,width=1]
  CCPROD,CEPROD,LSPROD[label=<PROD>,width=1]
  CurrencyCalculationService[label=<Currency Conversion Microservice>];
CurrencyExchangeService[label=<Currency Exchange Microservice>];
MicroserviceX[label=<Microservice X>]

}

NAMINGSERVER
#######################
digraph architecture {
rankdir = TB;
node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"];

{rank=same; CurrencyCalculationService, CurrencyExchangeService};
CurrencyExchangeService -> NamingServer;
CurrencyCalculationService -> NamingServer;
MicroserviceX -> NamingServer
  CurrencyCalculationService[label=<Currency Conversion Microservice>];
CurrencyExchangeService[label=<Currency Exchange Microservice>];
MicroserviceX[label=<Microservice X>]
NamingServer[label=<Naming Server or Service Registry>]
}

ClientSideLoadBalancing
#######################
digraph architecture {
rankdir = TB;
node[shape=component]
node[shape="rect"]
node[style=filled,color="#59C8DE"];

{rank=same; CurrencyExchangeService1, CurrencyExchangeService2, CurrencyExchangeService3};
{rank=same; LoadBalancer, NamingServer };
LoadBalancer -> CurrencyExchangeService1
LoadBalancer -> CurrencyExchangeService2
LoadBalancer -> CurrencyExchangeService3
CurrencyCalculationService -> LoadBalancer
LoadBalancer -> NamingServer
CurrencyCalculationService[label=<Currency Conversion Microservice>];
CurrencyExchangeService1[label=<Currency Exchange - Instance 1>];
CurrencyExchangeService2[label=<Currency Exchange - Instance 2>];
CurrencyExchangeService3[label=<Currency Exchange - Instance 3>];
LoadBalancer[label=<Load Balancer>]
NamingServer[label=<Naming Server>]
}

ZipkinDistributedTracingServer
###############################
digraph architecture {
rankdir = TB;
node[shape=component]
node[shape="rect"]
node[style=filled,color="#59C8DE"];

Database[shape=cylinder]
{rank=same; APIGateway, CurrencyCalculationService, CurrencyExchangeService};
APIGateway -> CurrencyCalculationService -> CurrencyExchangeService
CurrencyExchangeService -> ZipkinDistributedTracingServer
CurrencyCalculationService -> ZipkinDistributedTracingServer;
APIGateway -> ZipkinDistributedTracingServer
ZipkinDistributedTracingServer -> Database

CurrencyCalculationService[label=<Currency Conversion Microservice>];
CurrencyExchangeService[label=<Currency Exchange Microservice>];
APIGateway[label=<API Gateway>];
ZipkinDistributedTracingServer[label=<Distributed Tracing Server>];
}


digraph architecture {
rankdir = TB;
node[shape=component]
node[shape="rect"]
node[style=filled,color="#59C8DE"];

Database[shape=cylinder]
RabbitMQ[shape=underline,style=unfilled,color="#000000"]
{rank=same; APIGateway, CurrencyCalculationService, CurrencyExchangeService};
APIGateway -> CurrencyCalculationService -> CurrencyExchangeService
CurrencyExchangeService -> RabbitMQ
CurrencyCalculationService -> RabbitMQ;
APIGateway -> RabbitMQ
RabbitMQ -> ZipkinDistributedTracingServer
ZipkinDistributedTracingServer -> Database

  CurrencyCalculationService[label=<Currency Conversion Microservice>];
CurrencyExchangeService[label=<Currency Exchange Microservice>];
APIGateway[label=<API Gateway>];
RabbitMQ[label=<Rabbit MQ>];
ZipkinDistributedTracingServer[label=<Distributed Tracing Server>];
}

CurrencyExchangeMicroserviceDeployment
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~``
digraph architecture {
rankdir = TB;

node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"];

{rank=same; DEV, QA, STAGE, PROD};

DEV -> DEV1

QA -> QA1
QA -> QA2

STAGE -> STAGE1

PROD -> PROD1
PROD -> PROD2
PROD -> PROD3
PROD -> PROD4

}

CurrencyConversionMicroserviceDeployment
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~``
digraph architecture {
rankdir = TB;
node[shape=component]
node[shape=record]
node[style=filled,color="#59C8DE"];

{rank=same; DEV, QA, STAGE, PROD};

DEV -> DEV1

QA -> QA1
QA -> QA2

STAGE -> STAGE1

PROD -> PROD1

}


```

### Troubleshooting
- Refer our TroubleShooting Guide - https://github.com/in28minutes/in28minutes-initiatives/tree/master/The-in28Minutes-TroubleshootingGuide-And-FAQ

## Youtube Playlists - 500+ Videos

[Click here - 30+ Playlists with 500+ Videos on Spring, Spring Boot, REST, Microservices and the Cloud](https://www.youtube.com/user/rithustutorials/playlists?view=1&sort=lad&flow=list)

## Keep Learning in28Minutes

in28Minutes is creating amazing solutions for you to learn Spring Boot, Full Stack and the Cloud - Docker, Kubernetes, AWS, React, Angular etc. - [Check out all our courses here](https://github.com/in28minutes/learn)

![in28MinutesLearningRoadmap-July2019.png](https://github.com/in28minutes/in28Minutes-Course-Roadmap/raw/master/in28MinutesLearningRoadmap-July2019.png)
