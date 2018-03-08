package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.Doctor;

/**
 * Created by Zerir on 3/6/2018.
 */

public class DoctorUpdateProfile {

    String code, message;
    Doctor data;

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

    public Doctor getData() {
        return data;
    }

    public void setData(Doctor data) {
        this.data = data;
    }
}
