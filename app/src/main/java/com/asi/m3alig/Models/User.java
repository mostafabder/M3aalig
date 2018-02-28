package com.asi.m3alig.Models;

/**
 * Created by Luffy on 1/24/2018.
 */

public class User {
    String token;
    String secret;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
