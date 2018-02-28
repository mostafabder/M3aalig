package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.VisitStarted;

/**
 * Created by Zerir on 2/10/2018.
 */

public class StartingVisit {

    private String code, message;
    private VisitStarted data;

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

    public VisitStarted getVisitStarted() {
        return data;
    }

    public void setVisitStarted(VisitStarted visitStarted) {
        this.data = visitStarted;
    }
}
