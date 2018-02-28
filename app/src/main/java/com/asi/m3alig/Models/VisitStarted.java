package com.asi.m3alig.Models;

/**
 * Created by Zerir on 2/10/2018.
 */

public class VisitStarted {

    private String id, status, is_finish, is_cancel, is_start, finish_at, time, date, type, reason,
            patient_id, doctor_id, order_id, user_id, created_at, updated_at;

    private DateTime start_at;

    private String doctor_rate, doctor_reason, visit_id;

    private String summary, medical_desc, long_obj, short_obj, treatment_plan, treatment_desc,
            need_follow, number_session, other;

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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(String is_finish) {
        this.is_finish = is_finish;
    }

    public String getIs_cancel() {
        return is_cancel;
    }

    public void setIs_cancel(String is_cancel) {
        this.is_cancel = is_cancel;
    }

    public String getIs_start() {
        return is_start;
    }

    public void setIs_start(String is_start) {
        this.is_start = is_start;
    }

    public String getFinish_at() {
        return finish_at;
    }

    public void setFinish_at(String finish_at) {
        this.finish_at = finish_at;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public DateTime getStart_at() {
        return start_at;
    }

    public void setStart_at(DateTime start_at) {
        this.start_at = start_at;
    }

}
