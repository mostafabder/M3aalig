package com.asi.m3alig.Responses;


import com.asi.m3alig.Models.User;

/**
 * Created by Luffy on 1/5/2018.
 */

public class NormalResponse {
    String code;
    String message;

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
}
