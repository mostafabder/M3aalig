package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.Patient;

/**
 * Created by Luffy on 3/4/2018.
 */

public class ProfileUpdateResponse {
    String code;
    String message;
    Patient data;

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

    public Patient getData() {
        return data;
    }

    public void setData(Patient data) {
        this.data = data;
    }
}
