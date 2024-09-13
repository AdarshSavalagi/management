package com.sitmng.management.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Request {
    @Id
    private String id;
    private String departmentId;

    private String title;
    private String subject;
    private String description;
    private int status ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Request(String departmentId, String title, String subject, String description) {
        this.departmentId = departmentId;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.status=1;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

/*
*
* status:
*  1. sent.
*  2. read.
*  3. processing.
*  4. closed
*
* */
