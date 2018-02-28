package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.DateOrTimeData;

/**
 * Created by Zerir on 2/11/2018.
 */

public class ChangeDateOrTime {

    String code, message;
    DateOrTimeData date;

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

    public DateOrTimeData getDate() {
        return date;
    }

    public void setDate(DateOrTimeData date) {
        this.date = date;
    }
}
