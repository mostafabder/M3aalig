package com.asi.m3alig.Models;

/**
 * Created by Luffy on 1/30/2018.
 */

public class DateTime {
    String date;
    String timezone_type;
    String timezone;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimezone_type() {
        return timezone_type;
    }

    public void setTimezone_type(String timezone_type) {
        this.timezone_type = timezone_type;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
