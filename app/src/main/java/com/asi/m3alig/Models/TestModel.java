package com.asi.m3alig.Models;

/**
 * Created by Dr.Vista on 1/10/2018.
 */

public class TestModel {


    public TestModel(String name, String phone, String number, String desc, String note, String price) {
        this.name = name;
        this.phone = phone;
        this.number = number;
        this.desc = desc;
        this.note = note;
        this.price = price;
    }

    /**
     * name : ahmed mohamed
     * phone : 010025256398
     * number : 12345678
     * desc : desc desc desc
     * note : note note note
     * price : 45
     */


    private String name;
    private String phone;
    private String number;
    private String desc;
    private String note;
    private String price;

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getNumber() {
        return number;
    }

    public String getDesc() {
        return desc;
    }

    public String getNote() {
        return note;
    }

    public String getPrice() {
        return price;
    }
}

