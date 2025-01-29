package com.personal.foodcost.exceptions;

public class RestaurantException extends Exception{

    private int statusCode;

    public RestaurantException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
