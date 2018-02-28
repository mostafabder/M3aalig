package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.DoctorRate;

/**
 * Created by Zerir on 2/8/2018.
 */

public class DoctorRateVisit {

    String code;
    String message;
    DoctorRate date;

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

    public DoctorRate getDate() {
        return date;
    }

    public void setDate(DoctorRate date) {
        this.date = date;
    }
}
