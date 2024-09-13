package com.sitmng.management.dto;

public class RequestDTO {
    private String title;
    private String subject;
    private String description;

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

    public RequestDTO(String title, String subject, String description) {
        this.title = title;
        this.subject = subject;
        this.description = description;
    }
}
