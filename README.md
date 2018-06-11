# SQL Demo

[![Build Status](https://travis-ci.org/ValentinTyhonov/sqlDemo.svg?branch=master)](https://travis-ci.org/ValentinTyhonov/sqlDemo)

This is a pretty simple web project with using Spring Boot and Spring Data JPA for demonstrating CRUD operations.

## Set Up
For setting up this service properties file should be loaded with next required properties:
```
spring.datasource.url:
spring.datasource.username:
spring.datasource.password:
```
Service can be run via terminal/command line with next command:
```
java –jar sqlDemo.jar –-spring.config.location=local.properties
```
