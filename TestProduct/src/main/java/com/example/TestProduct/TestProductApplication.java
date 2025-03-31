package com.example.TestProduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TestProductApplication {



	public static void main(String[] args) {
		SpringApplication.run(TestProductApplication.class, args);
	}

}
