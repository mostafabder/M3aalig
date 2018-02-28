package com.asi.m3alig.Responses;

import com.asi.m3alig.Models.User;

/**
 * Created by Luffy on 1/5/2018.
 */

public class LoginResponse {

    String code;
    String message;
    User data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser_credentials() {
        return data;
    }

    public void setUser_credentials(User user_credentials) {
        this.data = user_credentials;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
