package com.asi.m3alig.Models;

/**
 * Created by Zerir on 2/8/2018.
 */

public class OrderDetails {

    private String when_pain_start, farFromYou, pain_positon, painPlace, location_region,id;

    private String doctorName, doctorJob, reason, prescription, farObject, nearObject, plan, medicine;

    public OrderDetails(String when_pain_start, String farFromYou, String pain_positon, String painPlace, String location_region,String id) {
        this.when_pain_start = when_pain_start;
        this.farFromYou = farFromYou;
        this.pain_positon = pain_positon;
        this.painPlace = painPlace;
        this.location_region = location_region;
        this.id=id;
    }

    public OrderDetails(String doctorName, String doctorJob, String reason, String prescription, String farObject, String nearObject, String plan, String medicine) {
        this.doctorName = doctorName;
        this.doctorJob = doctorJob;
        this.reason = reason;
        this.prescription = prescription;
        this.farObject = farObject;
        this.nearObject = nearObject;
        this.plan = plan;
        this.medicine = medicine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getWhenPainStart() {
        return when_pain_start;
    }

    public void setWhenPainStart(String whenPainStart) {
        this.when_pain_start = whenPainStart;
    }

    public String getFarFromYou() {
        return farFromYou;
    }

    public void setFarFromYou(String farFromYou) {
        this.farFromYou = farFromYou;
    }

    public String getPainPosition() {
        return pain_positon;
    }

    public void setPainPosition(String painPosition) {
        this.pain_positon = painPosition;
    }

    public String getPainPlace() {
        return painPlace;
    }

    public void setPainPlace(String painPlace) {
        this.painPlace = painPlace;
    }

    public String getAddress() {
        return location_region;
    }

    public void setAddress(String address) {
        this.location_region = address;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorJob() {
        return doctorJob;
    }

    public void setDoctorJob(String doctorJob) {
        this.doctorJob = doctorJob;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getFarObject() {
        return farObject;
    }

    public void setFarObject(String farObject) {
        this.farObject = farObject;
    }

    public String getNearObject() {
        return nearObject;
    }

    public void setNearObject(String nearObject) {
        this.nearObject = nearObject;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plane) {
        this.plan = plane;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}


