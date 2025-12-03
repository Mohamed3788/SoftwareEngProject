package com.university.lms.StudyBuddy.assignment.dto;

import java.util.Objects;

public class GradeRequest {
    private Double grade;
    private String feedback;

    // Getters and Setters
    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeRequest that = (GradeRequest) o;
        return Objects.equals(grade, that.grade) && Objects.equals(feedback, that.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, feedback);
    }

    // toString
    @Override
    public String toString() {
        return "GradeRequest{" +
                "grade=" + grade +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
