package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.RateVisit;

/**
 * Created by Luffy on 1/30/2018.
 */

public class RateVisitResponse {
    String code;
    String message;
    RateVisit data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RateVisit getData() {
        return data;
    }

    public void setData(RateVisit data) {
        this.data = data;
    }
}
