package com.university.lms.StudyBuddy.assignment.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class AssignmentResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;

    private Long courseId;
    private String courseTitle;

    private Long createdBy;

    public AssignmentResponse() {
    }

    public AssignmentResponse(Long id, String title, String description, LocalDateTime dueDate, Long courseId, String courseTitle, Long createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    // Builder pattern
    public static AssignmentResponseBuilder builder() {
        return new AssignmentResponseBuilder();
    }

    public static class AssignmentResponseBuilder {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime dueDate;
        private Long courseId;
        private String courseTitle;
        private Long createdBy;

        AssignmentResponseBuilder() {
        }

        public AssignmentResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AssignmentResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AssignmentResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AssignmentResponseBuilder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public AssignmentResponseBuilder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public AssignmentResponseBuilder courseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
            return this;
        }

        public AssignmentResponseBuilder createdBy(Long createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public AssignmentResponse build() {
            return new AssignmentResponse(id, title, description, dueDate, courseId, courseTitle, createdBy);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentResponse that = (AssignmentResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(dueDate, that.dueDate) && Objects.equals(courseId, that.courseId) && Objects.equals(courseTitle, that.courseTitle) && Objects.equals(createdBy, that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate, courseId, courseTitle, createdBy);
    }

    // toString
    @Override
    public String toString() {
        return "AssignmentResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", createdBy=" + createdBy +
                '}';
    }
}
