package com.asi.m3alig.Responses;

/**
 * Created by Luffy on 1/31/2018.
 */

public class CreateVisitResponse {
    String code;
    String message;
    String visit_id;

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

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
