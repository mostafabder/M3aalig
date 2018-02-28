package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.Report;
import com.asi.m3alig.Models.Visit;

import java.util.List;

/**
 * Created by Luffy on 1/30/2018.
 */

public class  VisitResponse {
    String code;
    String message;
    List<Visit> data;
    Report report;
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

    public List<Visit> getData() {
        return data;
    }

    public void setData(List<Visit> data) {
        this.data = data;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
