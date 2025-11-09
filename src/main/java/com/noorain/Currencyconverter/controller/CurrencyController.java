package com.noorain.Currencyconverter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Welcome All , our springBoot app is running";
    }
}
