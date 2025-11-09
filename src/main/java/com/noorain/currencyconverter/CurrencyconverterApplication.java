package com.noorain.currencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// @SpringBootApplication tells Spring to scan all packages inside com.noorain.currencyconverter
@SpringBootApplication
public class CurrencyconverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyconverterApplication.class, args);
		System.out.println("Application started successfully");
	}
}
