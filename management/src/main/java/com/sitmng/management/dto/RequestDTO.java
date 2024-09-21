package com.sitmng.management.dto;

import java.time.LocalDateTime;

public class RequestDTO {
    private String title;
    private String subject;
    private String description;
    private LocalDateTime issued;

    public LocalDateTime getIssued() {
        return issued;
    }

    public void setIssued(LocalDateTime issued) {
        this.issued = issued;
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

    public RequestDTO(String title, String subject, String description, LocalDateTime issued) {
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.issued = issued;
    }
}
