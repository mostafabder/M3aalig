package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.Visit;

/**
 * Created by Luffy on 2/9/2018.
 */

public class CurrentResponse {
    String code;
    String status;
    String message;
    Visit data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Visit getData() {
        return data;
    }

    public void setData(Visit data) {
        this.data = data;
    }
}
