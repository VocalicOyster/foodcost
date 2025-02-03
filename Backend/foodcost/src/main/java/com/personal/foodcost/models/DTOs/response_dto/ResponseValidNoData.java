package com.personal.foodcost.models.DTOs.response_dto;

import com.personal.foodcost.models.DTOs.Response;

public class ResponseValidNoData extends Response {

    public ResponseValidNoData(int status, String message) {
        super(status, message);
    }
}