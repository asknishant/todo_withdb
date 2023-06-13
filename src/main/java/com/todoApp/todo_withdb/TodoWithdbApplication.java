package com.todoApp.todo_withdb;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class TodoWithdbApplication {

	@Bean
	@Scope("singleton")
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoWithdbApplication.class, args);
	}

}
/*
* Server calls the controller , controller calls the service, service calls the repository. repository(ORM, here JPA) transacts with db
* */