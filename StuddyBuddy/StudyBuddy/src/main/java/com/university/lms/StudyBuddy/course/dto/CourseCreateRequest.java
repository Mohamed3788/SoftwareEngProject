package com.university.lms.StudyBuddy.course.dto;

import com.university.lms.StudyBuddy.user.model.StudentClass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class CourseCreateRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private StudentClass assignedClass;

    @NotNull
    private Long teacherId;

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

    public StudentClass getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(StudentClass assignedClass) {
        this.assignedClass = assignedClass;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseCreateRequest that = (CourseCreateRequest) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && assignedClass == that.assignedClass && Objects.equals(teacherId, that.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, assignedClass, teacherId);
    }

    // toString
    @Override
    public String toString() {
        return "CourseCreateRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", assignedClass=" + assignedClass +
                ", teacherId=" + teacherId +
                '}';
    }
}
