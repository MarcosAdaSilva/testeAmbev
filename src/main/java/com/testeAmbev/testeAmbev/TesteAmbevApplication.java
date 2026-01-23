package com.testeAmbev.testeAmbev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TesteAmbevApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteAmbevApplication.class, args);
	}

}