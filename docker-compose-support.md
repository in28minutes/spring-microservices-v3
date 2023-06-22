# Built-in Docker Compose Support

Recently, in the Spring Boot 3.1.0 release, we received a new feature called built-in docker compose support, which allows us to simply add the `spring-boot-docker-compose` artifactId and then do mvn clean install, and then run the application, and it will automatically spin up the application by taking classpath compose.yml. It will shut down as soon as the application is terminated.

We may accomplish this by following the steps outlined below.

### 1. Add the `spring-boot-docker-compose` module to an existing or new project to enable working with containers using Docker Compose.  

  #### For Maven
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-docker-compose</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

  #### For Gradle
```json lines
dependencies {
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}
```
When this module is added as a dependency, Spring Boot will do the following actions:

* Look in your application classpath directory for `compose.yml` and other typical compose filenames (`docker-compose.yml`)
* Once the application is running, it will also call docker compose up with the identified `compose.yml` OR `docker-compose.yml`
* Create service connection beans for each container that is supported.
* When the programme is shut down, it will call `docker compose stop`

### 2. For existing application, we might have created docker compose file with the specific file name in an application class path, hence we need to add the following entry in the `application.properties` or `application.yml`

**application.properties**

```properties
spring.docker.compose.file=../docker-compose.yml
```

**application.yaml**
```yaml
spring:
  docker:
    compose:
      file: "../docker-compose.yml"
```

### 3. Controlling lifecycle of Docker Compose

When your application begins, Spring Boot runs docker compose up, and when it stops, it calls docker compose stop. You may use the `spring.docker.compose.lifecycle-management` property to have various lifecycle management.

The following values are acceptable:

1. `start-only:` When the application starts, start the Docker Compose and leave it running
2. `start-and-stop:` Start and Stop the Docker Compose when the application starts and stops (jvm exits)
3. `none:` Don't start/stop Docker Compose.

### 4. Using Docker Compose Profiles

Docker Compose Profiles, like Spring profiles, may be activated depending on environment-specific parameters. All we have to do is add the following entry into the `application.properties` or `application.yml`

**application.properties**

```properties
spring.docker.compose.profiles.active=dev
```

**application.yaml**
```yaml
spring:
  docker:
    compose:
      profiles:
        active: "dev"

```

