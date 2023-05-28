package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

public record HelloWorldBean(String message) {

    @Override
    public String toString() {
        return "HelloWorldBean [message=" + message + "]";
    }

}
