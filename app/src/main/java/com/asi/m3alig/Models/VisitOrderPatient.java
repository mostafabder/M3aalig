package com.asi.m3alig.Models;

import java.io.Serializable;

/**
 * Created by Luffy on 1/24/2018.
 */

public class VisitOrderPatient implements Serializable {
    String age;
    String gender;
    String who_need_session;
    String social_statue;
    String move_level;
    String health_problem;
    String when_pain_start;
    String pain_position;
    String pain_deep;
    String location_floor_number;
    String location_street_name;
    String location_region;
    String location_city;
    String lat;
    String lng;
    String type;
    String time;
    String date;


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWho_need_session() {
        return who_need_session;
    }

    public void setWho_need_session(String who_need_session) {
        this.who_need_session = who_need_session;
    }

    public String getSocial_statue() {
        return social_statue;
    }

    public void setSocial_statue(String social_statue) {
        this.social_statue = social_statue;
    }

    public String getMove_level() {
        return move_level;
    }

    public void setMove_level(String move_level) {
        this.move_level = move_level;
    }

    public String getHealth_problem() {
        return health_problem;
    }

    public void setHealth_problem(String health_problem) {
        this.health_problem = health_problem;
    }

    public String getWhen_pain_start() {
        return when_pain_start;
    }

    public void setWhen_pain_start(String when_pain_start) {
        this.when_pain_start = when_pain_start;
    }

    public String getPain_position() {
        return pain_position;
    }

    public void setPain_position(String pain_position) {
        this.pain_position = pain_position;
    }

    public String getPain_deep() {
        return pain_deep;
    }

    public void setPain_deep(String pain_deep) {
        this.pain_deep = pain_deep;
    }

    public String getLocation_floor_number() {
        return location_floor_number;
    }

    public void setLocation_floor_number(String location_floor_number) {
        this.location_floor_number = location_floor_number;
    }

    public String getLocation_street_name() {
        return location_street_name;
    }

    public void setLocation_street_name(String location_street_name) {
        this.location_street_name = location_street_name;
    }

    public String getLocation_region() {
        return location_region;
    }

    public void setLocation_region(String location_region) {
        this.location_region = location_region;
    }

    public String getLocation_city() {
        return location_city;
    }

    public void setLocation_city(String location_city) {
        this.location_city = location_city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "["+"who "+getWho_need_session()+" /"+
                "gender "+getGender()+" /"+
                "age "+getAge()+" /"+
                "ss "+getSocial_statue()+" /"+
                "ml "+getMove_level()+" /" +
                "hp "+getHealth_problem()+"/ "+
                "when "+getWhen_pain_start()+"/ "+
                "pp "+getPain_position()+"/ "+
                "locf "+getLocation_floor_number()+"/ "+
                "locs "+getLocation_street_name()+" /"+
                "locr "+getLocation_region()+" /"+
                "locc "+getLocation_city()+"/"+
                "lat "+getLat()+"/"+
                "lng "+getLng()+" /"+
                "type "+getType()+" /"+
                "time "+getTime()+"/ "+
                "date "+getDate()+"]";
    }
}
