package com.kpi.lab0;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@Order(2)
public class Lab0Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Lab0Application.class, args);
		System.out.println("End of main");
	}

	@Override
	public void run(String... args) {
		System.out.println("Hello world from lab0!");
	}
}
