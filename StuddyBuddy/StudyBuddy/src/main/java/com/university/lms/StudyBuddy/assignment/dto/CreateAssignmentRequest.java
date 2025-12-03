package com.university.lms.StudyBuddy.assignment.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CreateAssignmentRequest {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Long courseId;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
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
        CreateAssignmentRequest that = (CreateAssignmentRequest) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(dueDate, that.dueDate) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, dueDate, courseId);
    }

    // toString
    @Override
    public String toString() {
        return "CreateAssignmentRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", courseId=" + courseId +
                '}';
    }
}

