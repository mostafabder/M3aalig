package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.DoctorSingleVisit;

import java.util.List;

/**
 * Created by Zerir on 2/8/2018.
 */

public class DoctorVisits {

    String code;
    String message;
    List<DoctorSingleVisit> data;

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

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
