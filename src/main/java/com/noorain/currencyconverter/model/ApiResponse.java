package com.noorain.currencyconverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// Generic wrapper for sending consistent responses
@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;

//    public ApiResponse(String conversionSuccessfull, T convertAmount) {
//    }
}

/*

Why?
Every API should return a standard format like this:

json:

{
  "message": "Conversion successful",
  "data": {
      "from": "USD",
      "to": "INR",
      "convertedAmount": 8342.5
  }
}
That’s how clean APIs look in professional systems.

 */