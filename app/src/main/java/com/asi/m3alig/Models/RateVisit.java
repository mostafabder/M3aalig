package com.asi.m3alig.Models;

/**
 * Created by Luffy on 1/30/2018.
 */

public class RateVisit {

    String patient_rate;
    String patient_reason;
    String visit_id;
    String id;

    public String getPatient_rate() {
        return patient_rate;
    }

    public void setPatient_rate(String patient_rate) {
        this.patient_rate = patient_rate;
    }

    public String getPatient_reason() {
        return patient_reason;
    }

    public void setPatient_reason(String patient_reason) {
        this.patient_reason = patient_reason;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
