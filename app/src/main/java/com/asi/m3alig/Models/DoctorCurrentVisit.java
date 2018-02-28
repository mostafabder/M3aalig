package com.asi.m3alig.Models;

/**
 * Created by Zerir on 2/9/2018.
 */

public class DoctorCurrentVisit {

    String code;
    String message;
    DoctorSingleVisit data;

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

    public DoctorSingleVisit getData() {
        return data;
    }

    public void setData(DoctorSingleVisit data) {
        this.data = data;
    }

}
