package com.in28minutes.microservices.limitsservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, and @Setter on all non-final fields and @RequiredArgsConstructor
@AllArgsConstructor // Generates constructors that take one argument for every field.
public class Limits {
	private int minimum;
	private int maximum;
}
