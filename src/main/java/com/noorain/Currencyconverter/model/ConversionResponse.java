package com.noorain.Currencyconverter.model;


public class ConversionResponse {
    private String from;
    private String to;
    private double amount;
    private double rate;
    private double convertedAmount;
    private String source; // which API provided the rate
}
