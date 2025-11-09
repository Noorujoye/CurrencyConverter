package com.noorain.currencyconverter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// @Configuration means this class provides Bean definitions to the Spring container
@Configuration
public class RestTemplateConfig {

    @Bean // @Bean makes this RestTemplate instance reusable across the app
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
