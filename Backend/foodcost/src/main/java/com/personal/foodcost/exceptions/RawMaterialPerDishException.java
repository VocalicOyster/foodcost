package com.personal.foodcost.exceptions;

public class RawMaterialPerDishException extends Exception {

    private int statusCode;

    public RawMaterialPerDishException(String message, int statusCode) {

        super(message);
        this.statusCode = statusCode;
    }


}
