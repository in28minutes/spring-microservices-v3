# Implementing Lombok into the Project

The Lombok library is a popular Java library that aims to reduce the amount of boilerplate code that is typically required by Java applications when working with objects. Lombok provides a set of annotations that can be placed on class fields, methods, or entire classes that can be used to automatically generate the code for these fields, methods, or entire classes as a result of the annotations.

Let's consider a simple example to illustrate the usage of Lombok. Suppose we have a class called `Course` that represents a course's information, such as their courseId, and courseName.

**Without Lombok**, we would need to write a lot of repetitive code for `getters, setters, constructors, and other common methods`. However, with Lombok, we can simplify this process.

First, we need to include the Lombok library in our project's dependencies in `pom.xml`

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

Once we have done that, we can use Lombok annotations to generate the required code. Here's how the Person class would look like with Lombok:

```java
import lombok.Data;

@Data
public class Course {
    private Long courseId;
    private String courseName;
}
```
In the above example, we have used the `@Data` annotation provided by Lombok. This annotation automatically generates the following methods for us:

* Getter methods for all the fields (`getCourseId()`, `getCourseName()`)
* Setter methods for all the fields (`setCourseId()`, `setCourseName()`)
* A default no-argument constructor (`Course()`)
* A constructor with arguments for all the fields (`Course(String courseId, String courseName)`)
* toString(), equals(), and hashCode() methods, among others

We don't have to write these methods ourselves using Lombok. During the compilation phase, Lombok takes care of producing them. This decreases code verbosity, resulting in more succinct and understandable code.

We can focus on the important portions of our code and let Lombok handle the repetitive tasks, saving time and effort in development.

Here is the comprehensive list of annotations from lombok

1. `@Getter and @Setter`: These annotations generate getter and setter methods for the fields of a class. By placing @Getter on a field, Lombok generates the corresponding getter method (getField()) and @Setter generates the setter method (setField(value)).

2. `@ToString`: This annotation generates a toString() method for the class, which returns a string representation of the object. It includes a comma-separated list of the class's field names and their values.

3. `@EqualsAndHashCode`: This annotation generates equals() and hashCode() methods based on the fields of the class. The equals() method checks for equality between objects, and the hashCode() method generates a hash code for the object.

4. `@NoArgsConstructor, @RequiredArgsConstructor, and @AllArgsConstructor`: These annotations generate constructors for the class. @NoArgsConstructor generates a no-argument constructor, @RequiredArgsConstructor generates a constructor for the final and @NonNull fields, and @AllArgsConstructor generates a constructor for all fields.

5. `@Builder`: This annotation generates a builder pattern for the class. It provides a way to create objects using a fluent API, allowing you to set field values in a chained manner.

6. `@NonNull`: This annotation can be used on a parameter or field to indicate that it should not be null. Lombok generates null checks and throws a NullPointerException if the annotated element is null.

7. `@Value`: This annotation is similar to @Data, but it creates an immutable class. It generates final fields, a constructor initializing all fields, and getters.

8. `@Slf4j`: This annotation integrates the Simple Logging Facade for Java (SLF4J) into the class. It generates a logger field with the name log, which can be used to log messages.

## Integrated in Spring Microservices V3

We have implemented the **Lombok Feature** in the following Pull Request(PR) for all the projects in [spring-microservices-v3](https://github.com/in28minutes/spring-microservices-v3/pull/4/files)


For more details, please go through the [Project Lombok Documentation](https://projectlombok.org/features/)

Happy Learning and Coding...