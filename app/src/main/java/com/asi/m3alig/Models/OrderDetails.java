package com.asi.m3alig.Models;

/**
 * Created by Zerir on 2/8/2018.
 */

public class OrderDetails {

    private String when_pain_start, street, city, painPlace, location_region,id;
    private Patient patient;

    private String doctorName, doctorJob, reason, prescription, farObject, nearObject, plan, medicine;

    public OrderDetails(String id, String when_pain_start, String street, String city, String painPlace, String location_region, Patient patient) {
        this.id = id;
        this.when_pain_start = when_pain_start;
        this.street = street;
        this.city = city;
        this.painPlace = painPlace;
        this.location_region = location_region;
        this.patient = patient;
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

    public String getWhen_pain_start() {
        return when_pain_start;
    }

    public void setWhen_pain_start(String when_pain_start) {
        this.when_pain_start = when_pain_start;
    }

    public String getLocation_region() {
        return location_region;
    }

    public void setLocation_region(String location_region) {
        this.location_region = location_region;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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


