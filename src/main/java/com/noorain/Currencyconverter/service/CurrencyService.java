package com.noorain.Currencyconverter.service;

import com.noorain.Currencyconverter.config.RestTemplate;
import com.noorain.Currencyconverter.model.ConversionHistory;
import com.noorain.Currencyconverter.model.ConversionResponse;
import com.noorain.Currencyconverter.repository.ConversionHistoryRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service // this annotation marks
public class CurrencyService {
    private final RestTemplate restTemplate;
    private final ConversionHistoryRepo repo;
    private final ConcurrentHashMap<String , CacheRate> cache = new ConcurrentHashMap<>();
    private static final long CACHE_TTL_MS = 5*60*1000;

    public CurrencyService(RestTemplate restTemplate , ConversionHistoryRepo repo) {
        this.restTemplate = restTemplate;
        this.repo = repo;
    }

    public ConversionResponse convert(String from , String to , double amout) {
        String key = from + "_" + to;
        double rate = getRate(from , to , key);

        double converted = amount * rate;

        //saving it to database

        ConversionHistory history = ConversionHistory.builder()
                .fromCurrency(from)
                .toCurrency(to)
                .amount(amount)
                .convertedAmount(converted)
                .timesramp(LocalDateTime.now())
                .build();
        repo.save(history);
    }
}