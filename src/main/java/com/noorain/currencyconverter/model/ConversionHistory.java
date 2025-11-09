package com.noorain.currencyconverter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//This will be stored in our H2 database.
//We’ll use it to record every successful currency conversion for history retrieval.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment ID
    private Long id;

    private String fromCurrency;
    private String toCurrency;
    private double amount;
    private double rate;
    private double convertedAmount;
    private String source; // which API provided the rate
    private LocalDateTime timestamp;

}
