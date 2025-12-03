package com.university.lms.StudyBuddy.course.dto;

import com.university.lms.StudyBuddy.user.model.StudentClass;

import java.util.Objects;

public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private StudentClass assignedClass;

    private Long teacherId;
    private String teacherName;

    public CourseResponse() {
    }

    public CourseResponse(Long id, String title, String description, StudentClass assignedClass, Long teacherId, String teacherName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedClass = assignedClass;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    // Builder pattern
    public static CourseResponseBuilder builder() {
        return new CourseResponseBuilder();
    }

    public static class CourseResponseBuilder {
        private Long id;
        private String title;
        private String description;
        private StudentClass assignedClass;
        private Long teacherId;
        private String teacherName;

        CourseResponseBuilder() {
        }

        public CourseResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseResponseBuilder assignedClass(StudentClass assignedClass) {
            this.assignedClass = assignedClass;
            return this;
        }

        public CourseResponseBuilder teacherId(Long teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public CourseResponseBuilder teacherName(String teacherName) {
            this.teacherName = teacherName;
            return this;
        }

        public CourseResponse build() {
            return new CourseResponse(id, title, description, assignedClass, teacherId, teacherName);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseResponse that = (CourseResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && assignedClass == that.assignedClass && Objects.equals(teacherId, that.teacherId) && Objects.equals(teacherName, that.teacherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, assignedClass, teacherId, teacherName);
    }

    // toString
    @Override
    public String toString() {
        return "CourseResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", assignedClass=" + assignedClass +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
