package com.university.lms.StudyBuddy.assignment.dto;

import java.util.Objects;

public class SubmitAssignmentRequest {
    private String content;  // text or a link to uploaded file

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmitAssignmentRequest that = (SubmitAssignmentRequest) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    // toString
    @Override
    public String toString() {
        return "SubmitAssignmentRequest{" +
                "content='" + content + '\'' +
                '}';
    }
}

