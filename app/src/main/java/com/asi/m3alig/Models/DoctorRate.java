package com.asi.m3alig.Models;

/**
 * Created by Zerir on 2/8/2018.
 */

public class DoctorRate {

    String doctor_rate;
    String doctor_reason;
    String visit_id;
    String updated_at;
    String created_at;
    String id;

    public String getDoctor_rate() {
        return doctor_rate;
    }

    public void setDoctor_rate(String doctor_rate) {
        this.doctor_rate = doctor_rate;
    }

    public String getDoctor_reason() {
        return doctor_reason;
    }

    public void setDoctor_reason(String doctor_reason) {
        this.doctor_reason = doctor_reason;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
