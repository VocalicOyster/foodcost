package com.personal.foodcost.exceptions;

public class RawMaterialException extends Exception{

    private int statusCode;

    public RawMaterialException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
