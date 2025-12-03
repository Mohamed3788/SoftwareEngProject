package com.university.lms.StudyBuddy.module.dto;

import java.util.Objects;

public class CreateModuleRequest {
    private String title;
    private Long courseId;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateModuleRequest that = (CreateModuleRequest) o;
        return Objects.equals(title, that.title) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, courseId);
    }

    // toString
    @Override
    public String toString() {
        return "CreateModuleRequest{" +
                "title='" + title + '\'' +
                ", courseId=" + courseId +
                '}';
    }
}

