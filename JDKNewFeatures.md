# JDK New Features

## 1. Record Feature

The Java Record feature, introduced in Java 14, is a language feature that provides a short and convenient way to create classes for storing data. It makes it easier to create simple data-centric classes by automatically generating common methods like 

* Constructors
* Accessors (getters)
* Equals(), HashCode()
* and toString()



## Declaration
The **record** keyword is used to declare a Java record, followed by _the name of the record class_ and a _parameter list describing the fields_.

```java
record Course(int courseId, String courseName) {
   // Record body (optional)
}
```

## Field Accessors/Getters

A record's fields are implicitly declared to be **private** and **final**. The accessor methods (getters) for the fields are generated internally by compiler automatically, letting you retrieve the data.

```java
Course course = new Course(1, "Spring Boot Microservices");
int courseId = course.courseId();      // Accessing field using generated accessor
String name = course.courseName();    // Accessing field using generated accessor
```

# Note

We have implemented the **Java Record Feature** in the following Pull Request(PR) for all the projects in [spring-microservices-v3](https://github.com/in28minutes/spring-microservices-v3/pull/3/files):
