package com.personal.foodcost.exceptions;

public class DishException extends Exception {
    private int statusCode;

    public DishException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
