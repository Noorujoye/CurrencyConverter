package com.noorain.currencyconverter.controller;

import com.noorain.currencyconverter.model.ApiResponse;
import com.noorain.currencyconverter.model.ConversionHistory;
import com.noorain.currencyconverter.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  // Tells Spring this class handles REST endpoints
@RequestMapping("/api/currency") // Common prefix for all currency-related APIs
@RequiredArgsConstructor  // Injects CurrencyService via constructor
public class CurrencyController {
    private static final Logger log = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyService currencyService; // autowired service layer

//    public CurrencyController(CurrencyService currencyService) {
//        this.currencyService = currencyService;
//    }

    @GetMapping("/convert")
    public ResponseEntity<ApiResponse<Double>> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount) {

        log.debug("Received convert request from={} to={} amount={}", from, to, amount);

        //call service method to perform the actual conversion logic
        double convertAmount = currencyService.convert(from.toUpperCase(), to.toUpperCase(), amount);

        // wrap response in a clean API format
        ApiResponse<Double> response = new ApiResponse<>(
                "Conversion successfull",
                convertAmount
        );
        //return http 200 ok with json body
        return ResponseEntity.ok(response);
    }
    //  Endpoint: GET /api/currency/history
    // Description: Fetch all past conversion records
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<ConversionHistory>>> getHistory() {
        List<ConversionHistory> historyList = currencyService.getAllHistory();

        //handle empty database

        if (historyList.isEmpty()) {
            return ResponseEntity.ok(
                    new ApiResponse<>("No conversion history found" , historyList)
            );
        }
        return ResponseEntity.ok(
                new ApiResponse<>("Conversion history fetched successfully", historyList)
        );
    }
}
