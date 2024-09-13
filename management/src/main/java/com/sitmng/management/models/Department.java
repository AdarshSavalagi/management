package com.sitmng.management.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "departments")
public class Department {
    @Id
    private String id;
    private String name;

    private String code;// this is like a username using which they will login
    private String password_hash;

    private String token;
    private LocalDateTime tokenExpires;

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTokenExpires() {
        return tokenExpires;
    }

    public void setTokenExpires(LocalDateTime tokenExpires) {
        this.tokenExpires = tokenExpires;
    }

    public Department(  String password_hash, String code, String name) {

        this.password_hash = password_hash;
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

