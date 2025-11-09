package com.noorain.currencyconverter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.noorain.currencyconverter.model.ConversionHistory;
import com.noorain.currencyconverter.repository.ConversionHistoryRepo;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service // this annotation marks this class as a spring-managed service bean
//@RequiredArgsConstructor //Auto-generates a constructor for all final fields
public class CurrencyService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyService.class);
    // Injecting RestTemplate bean (we created it in config/RestTemplate.java)
    private final RestTemplate restTemplate; //provided by RestTemplate

    // Injecting repository to save conversion history into the database
    private final ConversionHistoryRepo repo;

    public CurrencyService(RestTemplate restTemplate, ConversionHistoryRepo repo) {
        this.restTemplate = restTemplate;
        this.repo = repo;
    }

    // Main method to perform conversion between two currencies
    public double convert(String from, String to, double amount) {

        //Basic validation
        if (from == null || to == null) {
            throw new IllegalArgumentException("from and to parameters are required");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }

        // 1st build API url
        // External API endpoint (gets exchange rates based on base currency)
        String url = "https://api.exchangerate-api.com/v4/latest/" + from;
        log.debug("Calling external API: {}", url);

        try {
            // 2️nd Fetch live exchange rates
            //Call the external API using restTemplate and parse the response into a map
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null || !response.containsKey("rates")) {
                throw new RuntimeException(("Invalid response from exchange rate API"));
            }

            @SuppressWarnings("unchecked")
            // 3rd Extract "rates" map from JSON
            // Extract the "rates" object from the JSON (contains currency-rate pairs)
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");

            if (!rates.containsKey(to)) {
                throw new RuntimeException("Target currency not found in rates: " + to);
            }




            //Get the conversion rate for the target currency(e.g., INR)
            Object rate = rates.get(to);
            double rate1;
            if (rate instanceof Number) {
                rate1 = ((Number) rate).doubleValue();
            } else {
                throw new RuntimeException("Unexpected rate value type for " + to);
            }

            //perform the conversion
            double convertedAmount = amount * rate1;
            log.debug("Rate {} -> {} = {}, convertedAmount = {}", from, to, rate1, convertedAmount);


            //saving this conversion record into our H2 Database
                    ConversionHistory history = ConversionHistory.builder()
                            .fromCurrency(from)
                            .toCurrency(to)
                            .amount(amount)
                            .convertedAmount(convertedAmount)
                            .timestamp(LocalDateTime.now())
                            .build();

                            repo.save(history);
            log.debug("saved conversion history id={}", history.getId());

            //Return the final converted amount to the controller
            return convertedAmount;
        } catch (RestClientException e) {
            log.error("external APU call failed: {}" , e.getMessage(), e);
            throw new RuntimeException("failed to call exchange rate API: " + e.getMessage());
        }
    }

    // Fetch conversion history from H2 database
    public List<ConversionHistory> getAllHistory() {
        List<ConversionHistory> historyList = repo.findAll();

        //sort it by latest timestamp descending
        historyList.sort((a , b) -> b.getTimestamp().compareTo(a.getTimestamp()));
        return historyList;
    }
}