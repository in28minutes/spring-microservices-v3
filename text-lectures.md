## Lecture 22: CODE BACKUP FILE : For Reference

Here's a complete code backup you can refer to if you have any problems with this section!

https://github.com/in28minutes/spring-microservices/blob/master/02.restful-web-services/2.3.1.RELEASE-upgrade.md

RECOMMENDATION: Do not forget to bookmark it.


## Lecture 30: COURSE UPDATE : HATEOAS Updates

VERSION UPDATES FOR NEXT LECTURE

There are a few modifications of HATEOAS in the latest release of Spring HATEOAS 1.0.0:

Option 1 : Spring Boot Release >= 2.2.0

Complete code: https://github.com/in28minutes/spring-microservices/blob/master/02.restful-web-services/2.3.1.RELEASE-upgrade.md#srcmainjavacomin28minutesrestwebservicesrestfulwebservicesuseruserresourcejava

```
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@RestController
public class UserJPAResource {


	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if(user==null)
			throw new UserNotFoundException("id-"+ id);
		
		
		//"all-users", SERVER_PATH + "/users"
		//retrieveAllUsers
		EntityModel<User> resource = EntityModel.of(user);
		
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		//HATEOAS
		
		return resource;
	}
```

Option 2: Older versions
```
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
 
Resource<User> resource = new Resource<User>(user);
ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
resource.add(linkTo.withRel("all-users"));
return resource;
```

## Lecture 36 - COURSE UPDATE : Disable XML Format Support

RECOMMENDATION: Disable XML Support

Remove this from pom.xml
```
<dependency>
	<groupId>com.fasterxml.jackson.dataformat</groupId>
	<artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

PROBLEM: Browsers give preference to XML over JSON (They send Accept header with application/xml).  

However, I prefer seeing responses with JSON in the browser (For most people REST = JSON)


## Lecture 37 - COURSE UPDATE : USE SWAGGER 3.0.0 for Spring Boot 2.2+

If you are using Spring Boot Version >= 2.2, We recommend using SpringFox Swagger version 3.0.0.

NEW URL for SWAGGER UI - http://localhost:8080/swagger-ui/ or http://localhost:8080/swagger-ui/index.html

Example pom.xml with the changes here - https://github.com/in28minutes/spring-microservices/blob/master/02.restful-web-services/2.3.1.RELEASE-upgrade.md#pomxml

Example SwaggerConfig with the changes here - https://github.com/in28minutes/spring-microservices/blob/master/02.restful-web-services/2.3.1.RELEASE-upgrade.md#srcmainjavacomin28minutesrestwebservicesrestfulwebservicesswaggerconfigjava

Add these dependencies to pom.xml instead of the two swagger dependencies.

```
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-boot-starter</artifactId>
	<version>3.0.0</version>
</dependency>
```

## Lecture 41 - COURSE UPDATE : Use spring-data-rest-hal-explorer

Update for Next Lecture:

If you are using Spring Boot version >2.4.0

Please add the below dependency instead of spring-data-rest-hal-browser

```
<dependency>
<groupId>org.springframework.data</groupId>
<artifactId>spring-data-rest-hal-explorer</artifactId>
</dependency>
```

## Lecture 49 - COURSE UPDATE : H2 Database URL & Data.sql

With the latest versions of Spring Boot

1) H2 database name is randomly generated each time you restart the server. You can find the database name and URL from the console log.

2) To use data.sql, you need to add this to application.properties -  spring.jpa.defer-datasource-initialization=true

RECOMMENDED: 

Configure this in application.properties:

```
spring.datasource.url=jdbc:h2:mem:testdb
spring.data.jpa.repositories.bootstrap-mode=default
spring.jpa.defer-datasource-initialization=true
```

DEBUGGING GUIDE (If you have problems)

JPA Hibernate Debugging Guide: https://github.com/in28minutes/in28minutes-initiatives/blob/master/The-in28Minutes-TroubleshootingGuide-And-FAQ/jpa-and-hibernate.md

Why do we need to configure bootstrap-mode? Details here - https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.3-Release-Notes#bootstrapmode-for-jpa-repositories


## Lecture 67 - Remember: Spring Cloud 2021.0.2 Update - Spring Cloud Config Server

With recent changes, if you are using spring-cloud-starter-config then you need to this in your application.properties:

spring.config.import=optional:configserver:http://localhost:8888

Please add this in every microservice where we are adding in spring-cloud-starter-config!


## Lecture 76 - COURSE UPDATE : Limits service with >=2.4.0 of SPRING BOOT
If you are using 2.4.0, you need to add this dependency to the pom.xml:

HINT: Make sure that you RESTART the server after saving your pom.xml

```
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```

## Lecture 78 - Debugging problems with Spring Cloud Config Server

If you are using 2.4.0, you need to add this dependency to the pom.xml:

```
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```

Debugging microservices problems can be difficult as there are multiple components involved. Step by Step instructions is provided in the troubleshooting guide to help you troubleshoot frequently occurring problems. Using the Chrome Browser is recommended.

https://github.com/in28minutes/in28minutes-initiatives/tree/master/The-in28Minutes-TroubleshootingGuide-And-FAQ#debugging-problems-with-spring-cloud-config-server

## Lecture 93: COURSE UPDATE : Ribbon DOES NOT work with Spring Boot 2.4

Ribbon is NO LONGER supported in the latest releases of Spring Boot and Spring Cloud.

In the meanwhile, if you want to use Ribbon, we recommend using 2.3.1.RELEASE with Hoxton.SR5.

```
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.1.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Hoxton.SR5</spring-cloud.version>
		<maven-jar-plugin.version>3.1.1</maven-jar-plugin.version>
	</properties>
```

## Lecture 95: Debugging problems with Feign and Ribbon

Debugging microservices problems can be difficult as there are multiple components involved. 

Step by Step instructions are provided in the troubleshooting guide to help you troubleshoot frequently occurring problems. 

Using Chrome Browser is recommended.

https://github.com/in28minutes/in28minutes-initiatives/tree/master/The-in28Minutes-TroubleshootingGuide-And-FAQ#debugging-problems-with-feign-and-ribbon

## Lecture 102 - Debugging Problems with Naming Server ( Eureka ) and Ribbon

Debugging microservices problems can be difficult as there are multiple components involved.

Step by Step instructions are provided in the troubleshooting guide to help you troubleshoot frequently occurring problems.

Using Chrome Browser is recommended.

https://github.com/in28minutes/in28minutes-initiatives/tree/master/The-in28Minutes-TroubleshootingGuide-And-FAQ#debugging-problems-with-naming-server-eureka-and-ribbon


## Lecture 109 - Debugging Problems with Zuul API Gateway

Debugging microservices problems can be difficult as there are multiple components involved.

Step by Step instructions are provided in the troubleshooting guide to help you troubleshoot frequently occurring problems.

Using Chrome Browser is recommended.

https://github.com/in28minutes/in28minutes-initiatives/tree/master/The-in28Minutes-TroubleshootingGuide-And-FAQ#debugging-problems-with-zuul-api-gateway


## Lecture 114 - Updates to Step 39 - Running Zipkin on Windows

In the next step, we set up our Zipkin Server by downloading a jar. 

If you get a 404 while downloading the jar, use the curl command to download :

```
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

For more information, please go through the below link:

https://zipkin.io/pages/quickstart

**ONLY FOR WINDOWS USERS**

If you are on Windows, this is important for you:

After you watch the next video, You can use the below commands to run Zipkin Server.

```
set RABBIT_URI=amqp://localhost
java -jar zipkin-server-2.7.0-exec.jar
```


## Lecture 119 - Debugging Problems with Zipkin

Debugging microservices problems can be difficult as there are multiple components involved.

Step by Step instructions are provided in the troubleshooting guide to help you troubleshoot frequently occurring problems.

Using Chrome Browser is recommended.

https://github.com/in28minutes/in28minutes-initiatives/tree/master/The-in28Minutes-TroubleshootingGuide-And-FAQ#debugging-problems-with-zipkin

## Lecture 125 - Course Downloads
Download the course material (presentation and code) for this section - https://github.com/in28minutes/course-material/blob/main/01-spring-microservices/v2/downloads.md

## Lecture 126 - Course Update: Additional Configuration to Spring Cloud Config Starter

Starting Spring Boot 2.5, to use spring-cloud-starter-config, you need this configuration in application.properties

```
spring.config.import=optional:configserver:http://localhost:8888
```

## Lecture 128: CODE BACKUP FILES and STEP BY STEP CHANGES : For Reference

Help for Debugging Problems:

Here's the code backup at the end of Step 07: https://github.com/in28minutes/spring-microservices-v2/blob/main/03.microservices/step07.md

Step by Step changes are detailed here:https://github.com/in28minutes/spring-microservices-v2/blob/main/03.microservices/01-step-by-step-changes/microservices-v2-1.md#step-01

Two Recommended Activities:

Activity - 1 : Explore other backups for this section (Steps 08,10,13,15,21,25,29, final) - https://github.com/in28minutes/spring-microservices-v2/tree/main/03.microservices

Activity - 2 : Get Familiar with the structure of Step by Step changes file - https://github.com/in28minutes/spring-microservices-v2/blob/main/03.microservices/01-step-by-step-changes/microservices-v2-1.md#step-01

## Lecture 134: Debugging problems with Spring Cloud Config Server - V2

Debugging microservices problems can be difficult as there are multiple components involved. 

Step by Step instructions is provided in the troubleshooting guide to help you troubleshoot frequently occurring problems. 

Using the Chrome Browser is recommended.

https://github.com/in28minutes/spring-microservices-v2/blob/main/03.microservices/01-step-by-step-changes/microservices-v2-1.md#spring-cloud-config-server---steps-01-to-08

## Lecture 140 - URL and Response Structure for Currency Exchange Service

We will make use of these in the next lecture!

URL - http://localhost:8000/currency-exchange/from/USD/to/INR

Response Structure

```
{
   "id":10001,
   "from":"USD",
   "to":"INR",
   "conversionMultiple":65.00,
   "environment":"8000 instance-id"
}
```

## Lecture 143 - Updates for data.sql - Spring Boot 2.5.0 or Greater

Summary

If you are using Spring Boot 2.5.0 or Greater, you have two options

1) Use schema.sql instead of data.sql  OR

2) Continue using data.sql. Add the following property to application.properties

```
spring.jpa.defer-datasource-initialization=true
```

Why do you need to make the change?

https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.5.0-M3-Release-Notes#hibernate-and-datasql

By default, data.sql scripts are now run before Hibernate is initialized. This aligns the behaviour of basic script-based initialization with that of Flyway and Liquibase. If you want to use data.sql to populate a schema created by Hibernate, set spring.jpa.defer-datasource-initialization to true. While mixing database initialization technologies is not recommended, this will also allow you to use a schema.sql script to build upon a Hibernate-created schema before it’s populated via data.sql.

## Lecture 153 - Debugging Problems with Eureka - V2

Debugging microservices problems can be difficult as there are multiple components involved. 

Step by Step instructions is provided in the troubleshooting guide to help you troubleshoot frequently occurring problems. 

Using the Chrome Browser is recommended.

COMPLETE DEBUGGING GUIDE

https://github.com/in28minutes/spring-microservices-v2/blob/main/03.microservices/01-step-by-step-changes/microservices-v2-1.md#eureka---step-19-to-21

Top Recommendation From Debugging Guide:

Give these settings a try individually in application.properties of all microservices (currency-exchange, currency-conversion) to see if they help

```
eureka.instance.prefer-ip-address=true
```


OR

```
eureka.instance.hostname=localhost
```


## Lecture 160 - Debugging Problems with Spring Cloud Gateway - V2

Debugging microservices problems can be difficult as there are multiple components involved. 

Step by Step instructions is provided in the troubleshooting guide to help you troubleshoot frequently occurring problems. 

Using the Chrome Browser is recommended.

COMPLETE DEBUGGING GUIDE

https://github.com/in28minutes/spring-microservices-v2/blob/main/03.microservices/01-step-by-step-changes/microservices-v2-1.md#spring-cloud-api-gateway---step-22-to-step-25

TOP Recommendation from Debugging Guide:

(6) Some student reported success when using lower-case-service-id instead of spring.cloud.gateway.discovery.locator.lowerCaseServiceId. See if it helps!

```
spring.cloud.gateway.discovery.locator.enabled=true

spring.cloud.gateway.discovery.locator.lower-case-service-id=true
```


## Lecture 166 - Update to Step 29 - Change in Configuration

Use maxAttempts instead of maxRetryAttempts

```
resilience4j.retry.instances.sample-api.maxAttempts=5 #NEW
#resilience4j.retry.instances.sample-api.maxRetryAttempts=5 #OLD
```

## Lecture 170 - RECOMMENDATION : Use PowerShell in Windows!

Recommendation 1

If you are using Windows, make sure that you use PowerShell instead of Command Prompt.

Recommendation 2

If you are using Window 10 and are using docker toolbox 

=> Use 192.168.99.100 instead of localhost.

Note: If 192.168.99.100 does not work, you can find the IP by using the command docker-machine ip 

Reason

In Window 10 when using docker toolbox, docker is configured to use the default machine with IP 192.168.99.100

## Lecture 183 - Link for the Next Lecture

In the next lecture, we will import projects into eclipse.

Here is the link to bookmark:

https://github.com/in28minutes/spring-microservices-v2/tree/main/04.docker

## Lecture 190 - Debugging Problems with Docker Compose

Debugging microservices problems can be difficult as there are multiple components involved. 

Step by Step instructions is provided in the troubleshooting guide to help you troubleshoot frequently occurring problems. 

COMPLETE DEBUGGING GUIDE

https://github.com/in28minutes/spring-microservices-v2/blob/main/03.microservices/01-step-by-step-changes/microservices-v2-1.md#docker-section---connect-microservices-with-zipkin

TOP Recommendation from Debugging Guide:

(2) Try adding restart: always to zipkin-server in docker-compose.yaml

```
  zipkin-server:
    image: openzipkin/zipkin:2.23
    mem_limit: 300m
    ports:
      - "9411:9411"
    networks:
      - currency-network
    environment:
      RABBIT_URI: amqp://guest:guest@rabbitmq:5672
    depends_on:
      - rabbitmq
    restart: always #Restart if there is a problem starting up
```

## Lecture 199 - Commands executed in this section

Here's a backup of commands executed in this section!

Refer to these if you face any problems!

You can bookmark this URL as well

https://github.com/in28minutes/spring-microservices-v2/tree/main/05.kubernetes#commands

```
docker run -p 8080:8080 in28min/hello-world-rest-api:0.0.1.RELEASE

kubectl create deployment hello-world-rest-api --image=in28min/hello-world-rest-api:0.0.1.RELEASE
kubectl expose deployment hello-world-rest-api --type=LoadBalancer --port=8080
kubectl scale deployment hello-world-rest-api --replicas=3
kubectl delete pod hello-world-rest-api-58ff5dd898-62l9d
kubectl autoscale deployment hello-world-rest-api --max=10 --cpu-percent=70
kubectl edit deployment hello-world-rest-api #minReadySeconds: 15
kubectl set image deployment hello-world-rest-api hello-world-rest-api=in28min/hello-world-rest-api:0.0.2.RELEASE

gcloud container clusters get-credentials in28minutes-cluster --zone us-central1-a --project solid-course-258105
kubectl create deployment hello-world-rest-api --image=in28min/hello-world-rest-api:0.0.1.RELEASE
kubectl expose deployment hello-world-rest-api --type=LoadBalancer --port=8080
kubectl set image deployment hello-world-rest-api hello-world-rest-api=DUMMY_IMAGE:TEST
kubectl get events --sort-by=.metadata.creationTimestamp
kubectl set image deployment hello-world-rest-api hello-world-rest-api=in28min/hello-world-rest-api:0.0.2.RELEASE
kubectl get events --sort-by=.metadata.creationTimestamp
kubectl get componentstatuses
kubectl get pods --all-namespaces

kubectl get events
kubectl get pods
kubectl get replicaset
kubectl get deployment
kubectl get service

kubectl get pods -o wide

kubectl explain pods
kubectl get pods -o wide

kubectl describe pod hello-world-rest-api-58ff5dd898-9trh2

kubectl get replicasets
kubectl get replicaset

kubectl scale deployment hello-world-rest-api --replicas=3
kubectl get pods
kubectl get replicaset
kubectl get events
kubectl get events --sort.by=.metadata.creationTimestamp

kubectl get rs
kubectl get rs -o wide
kubectl set image deployment hello-world-rest-api hello-world-rest-api=DUMMY_IMAGE:TEST
kubectl get rs -o wide
kubectl get pods
kubectl describe pod hello-world-rest-api-85995ddd5c-msjsm
kubectl get events --sort-by=.metadata.creationTimestamp

kubectl set image deployment hello-world-rest-api hello-world-rest-api=in28min/hello-world-rest-api:0.0.2.RELEASE
kubectl get events --sort-by=.metadata.creationTimestamp
kubectl get pods -o wide
kubectl delete pod hello-world-rest-api-67c79fd44f-n6c7l
kubectl get pods -o wide
kubectl delete pod hello-world-rest-api-67c79fd44f-8bhdt

gcloud container clusters get-credentials in28minutes-cluster --zone us-central1-c --project solid-course-258105
docker login
docker push in28min/mmv2-currency-exchange-service:0.0.11-SNAPSHOT
docker push in28min/mmv2-currency-conversion-service:0.0.11-SNAPSHOT

kubectl create deployment currency-exchange --image=in28min/mmv2-currency-exchange-service:0.0.11-SNAPSHOT
kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000
kubectl get svc
kubectl get services
kubectl get pods
kubectl get po
kubectl get replicaset
kubectl get rs
kubectl get all

kubectl create deployment currency-conversion --image=in28min/mmv2-currency-conversion-service:0.0.11-SNAPSHOT
kubectl expose deployment currency-conversion --type=LoadBalancer --port=8100

kubectl get svc --watch

kubectl get deployments

kubectl get deployment currency-exchange -o yaml >> deployment.yaml 
kubectl get service currency-exchange -o yaml >> service.yaml 

kubectl diff -f deployment.yaml
kubectl apply -f deployment.yaml

kubectl delete all -l app=currency-exchange
kubectl delete all -l app=currency-conversion

kubectl rollout history deployment currency-conversion
kubectl rollout history deployment currency-exchange
kubectl rollout undo deployment currency-exchange --to-revision=1

kubectl logs currency-exchange-9fc6f979b-2gmn8
kubectl logs -f currency-exchange-9fc6f979b-2gmn8 

kubectl autoscale deployment currency-exchange --min=1 --max=3 --cpu-percent=5 
kubectl get hpa

kubectl top pod
kubectl top nodes
kubectl get hpa
kubectl delete hpa currency-exchange

kubectl create configmap currency-conversion --from-literal=CURRENCY_EXCHANGE_URI=http://currency-exchange
kubectl get configmap

kubectl get configmap currency-conversion -o yaml >> configmap.yaml

watch -n 0.1 curl http://34.66.241.150:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

docker push in28min/mmv2-currency-conversion-service:0.0.12-SNAPSHOT
docker push in28min/mmv2-currency-exchange-service:0.0.12-SNAPSHOT
```

## Lecture 210: Link for the Next Lecture

In the next lecture, we will import projects into eclipse.

Here is the link to bookmark:

https://github.com/in28minutes/spring-microservices-v2/tree/main/05.kubernetes
