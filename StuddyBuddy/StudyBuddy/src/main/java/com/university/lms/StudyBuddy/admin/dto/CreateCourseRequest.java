package com.university.lms.StudyBuddy.admin.dto;

import com.university.lms.StudyBuddy.user.model.StudentClass;

import java.util.Objects;

public class CreateCourseRequest {

    private String title;
    private String description;
    private Long teacherId;
    private StudentClass assignedClass;

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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public StudentClass getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(StudentClass assignedClass) {
        this.assignedClass = assignedClass;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCourseRequest that = (CreateCourseRequest) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(teacherId, that.teacherId) && assignedClass == that.assignedClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, teacherId, assignedClass);
    }

    // toString
    @Override
    public String toString() {
        return "CreateCourseRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", teacherId=" + teacherId +
                ", assignedClass=" + assignedClass +
                '}';
    }
}
