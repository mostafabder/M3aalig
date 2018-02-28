package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.Orders;

import java.util.List;

/**
 * Created by Zerir on 2/7/2018.
 */

public class DoctorVisitsOrder {

    String code;
    List<Orders> data;

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Orders> getData() {
        return data;
    }

    public void setData(List<Orders> data) {
        this.data = data;
    }
}
