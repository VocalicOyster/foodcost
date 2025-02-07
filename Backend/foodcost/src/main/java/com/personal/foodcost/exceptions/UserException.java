package com.personal.foodcost.exceptions;

public class UserException extends Exception {

    private int statusCode;

    public UserException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
