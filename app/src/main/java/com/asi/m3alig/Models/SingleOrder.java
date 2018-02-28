package com.asi.m3alig.Models;

/**
 * Created by Zerir on 2/8/2018.
 */

public class SingleOrder {

    private String id, date, time;
    private OrderDetails orderDetails;
    //private boolean orderType;
    private String type, patientName;

    public SingleOrder(String id, String date, String time, OrderDetails orderDetails) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.orderDetails = orderDetails;
    }

    public SingleOrder(String type, String date, String time, String patientName, String id) {
        this.date = date;
        this.type = type;
        this.time = time;
        this.patientName = patientName;
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SingleOrder(String date, OrderDetails orderDetails, String type) {
        this.date = date;
        //this.orderDetails = orderDetails;
        this.type = type;
    }

    /*
    public boolean isOrderType() {
        return orderType;
    }

    public void setOrderType(boolean orderType) {
        this.orderType = orderType;
    }
    */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
}
