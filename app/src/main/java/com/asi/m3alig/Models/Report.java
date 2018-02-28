package com.asi.m3alig.Models;

/**

 =======
 * Created by Luffy on 2/9/2018.
 */

public class Report {

    String id;
    String summary;
    String medical_desc;
    String long_obj;
    String short_obj;
    String treatment_plan;
    String treatment_desc;
    String need_follow;
    String number_session;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMedical_desc() {
        return medical_desc;
    }

    public void setMedical_desc(String medical_desc) {
        this.medical_desc = medical_desc;
    }

    public String getLong_obj() {
        return long_obj;
    }

    public void setLong_obj(String long_obj) {
        this.long_obj = long_obj;
    }

    public String getShort_obj() {
        return short_obj;
    }

    public void setShort_obj(String short_obj) {
        this.short_obj = short_obj;
    }

    public String getTreatment_plan() {
        return treatment_plan;
    }

    public void setTreatment_plan(String treatment_plan) {
        this.treatment_plan = treatment_plan;
    }

    public String getTreatment_desc() {
        return treatment_desc;
    }

    public void setTreatment_desc(String treatment_desc) {
        this.treatment_desc = treatment_desc;
    }

    public String getNeed_follow() {
        return need_follow;
    }

    public void setNeed_follow(String need_follow) {
        this.need_follow = need_follow;
    }

    public String getNumber_session() {
        return number_session;
    }

    public void setNumber_session(String number_session) {
        this.number_session = number_session;
    }
}
