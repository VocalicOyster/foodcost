package com.personal.foodcost.exceptions;

public class IngredientException extends Exception {

    private int statusCode;

    public IngredientException(String message, int statusCode) {

        super(message);
        this.statusCode = statusCode;
    }


    public int getStatusCode() {
        return statusCode;
    }
}
