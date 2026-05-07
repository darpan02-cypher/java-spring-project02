package com.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
		System.out.println("Hello World");
	}

}


//flow of the application
//1.controller layer- it will receive the request from the client(web browser or postman) and then it will call the service layer to perform the business logic
//2.service layer- it will perform the business logic and then it will call the repository layer to perform the database operations
//3.repository layer- it will perform the database operations and then it will return the result to the service layer and then the service layer will return the result to the controller
//4.controller layer- it will return the response to the client     

//model- it will represent the data that we want to store in the database. It will be a class with some fields and getters and setters. It will be annotated with @Entity annotation to indicate that it is a JPA entity and it will be mapped to a table in the database. It will also have an @Id annotation to indicate the primary key of the entity.