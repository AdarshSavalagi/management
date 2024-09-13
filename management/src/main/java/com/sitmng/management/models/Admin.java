package com.sitmng.management.models;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Admin {
    @Id
    private String id;
    private String name;
    private String password_hash;
    private  String username;
    private String token;
    private LocalDateTime tokenExpiry;

    public Admin(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
