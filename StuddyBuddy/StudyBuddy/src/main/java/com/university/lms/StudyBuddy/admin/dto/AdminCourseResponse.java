package com.university.lms.StudyBuddy.admin.dto;

import com.university.lms.StudyBuddy.user.model.StudentClass;

import java.util.Objects;

public class AdminCourseResponse {

    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private String teacherName;
    private StudentClass assignedClass;

    public AdminCourseResponse() {
    }

    public AdminCourseResponse(Long id, String title, String description, Long teacherId, String teacherName, StudentClass assignedClass) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.assignedClass = assignedClass;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public StudentClass getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(StudentClass assignedClass) {
        this.assignedClass = assignedClass;
    }

    // Builder pattern
    public static AdminCourseResponseBuilder builder() {
        return new AdminCourseResponseBuilder();
    }

    public static class AdminCourseResponseBuilder {
        private Long id;
        private String title;
        private String description;
        private Long teacherId;
        private String teacherName;
        private StudentClass assignedClass;

        AdminCourseResponseBuilder() {
        }

        public AdminCourseResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AdminCourseResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AdminCourseResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AdminCourseResponseBuilder teacherId(Long teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public AdminCourseResponseBuilder teacherName(String teacherName) {
            this.teacherName = teacherName;
            return this;
        }

        public AdminCourseResponseBuilder assignedClass(StudentClass assignedClass) {
            this.assignedClass = assignedClass;
            return this;
        }

        public AdminCourseResponse build() {
            return new AdminCourseResponse(id, title, description, teacherId, teacherName, assignedClass);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminCourseResponse that = (AdminCourseResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(teacherId, that.teacherId) && Objects.equals(teacherName, that.teacherName) && assignedClass == that.assignedClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, teacherId, teacherName, assignedClass);
    }

    // toString
    @Override
    public String toString() {
        return "AdminCourseResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", assignedClass=" + assignedClass +
                '}';
    }
}
