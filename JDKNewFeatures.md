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
Course course = new Course(1, "Spring Boot With Microservices");
int courseId = course.courseId();      // Accessing field using generated accessor
String courseName = course.courseName();    // Accessing field using generated accessor
```

## Immutability
The fields in a record are final by default, making the record instances immutable. The values of the fields cannot be modified once they have been assigned.

## equals() & hashCode()
The produced `equals()` and `hashCode()` methods compare the values of the fields, offering a standard mechanism to check for equality between two record instances.
```java
Course courseOne = new Course(1, "Spring Boot With Microservices");
Course courseTwo = new Course(1, "Spring Boot With Microservices");
System.out.println(courseOne.equals(courseTwo)); // Output: true
```
## toString()

The `toString()` function returns a string representation of the record instance, including the field names and values.

```java
Course courseOne = new Course(1, "Spring Boot With Microservices");
System.out.println(courseOne); // Output: Course[courseId=1, courseName="Spring Boot With Microservices"]

```

In a nutshell, Java Records create basic data classes in a more compact and expressive manner, minimizing boilerplate code and enhancing readability. They are especially helpful for describing data transfer objects (DTOs), immutable data structures, and other situations in which data storage is the primary concern.
# Note

We have implemented the **Java Record Feature** in the following Pull Request(PR) for all the projects in [spring-microservices-v3](https://github.com/in28minutes/spring-microservices-v3/pull/3/files):
