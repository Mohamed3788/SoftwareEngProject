package com.university.lms.StudyBuddy.assignment.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubmissionResponse {
    private Long id;
    private String studentName;
    private String content;
    private LocalDateTime submittedAt;
    private Double grade;
    private String feedback;

    public SubmissionResponse() {
    }

    public SubmissionResponse(Long id, String studentName, String content, LocalDateTime submittedAt, Double grade, String feedback) {
        this.id = id;
        this.studentName = studentName;
        this.content = content;
        this.submittedAt = submittedAt;
        this.grade = grade;
        this.feedback = feedback;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

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

    // Builder pattern
    public static SubmissionResponseBuilder builder() {
        return new SubmissionResponseBuilder();
    }

    public static class SubmissionResponseBuilder {
        private Long id;
        private String studentName;
        private String content;
        private LocalDateTime submittedAt;
        private Double grade;
        private String feedback;

        SubmissionResponseBuilder() {
        }

        public SubmissionResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SubmissionResponseBuilder studentName(String studentName) {
            this.studentName = studentName;
            return this;
        }

        public SubmissionResponseBuilder content(String content) {
            this.content = content;
            return this;
        }

        public SubmissionResponseBuilder submittedAt(LocalDateTime submittedAt) {
            this.submittedAt = submittedAt;
            return this;
        }

        public SubmissionResponseBuilder grade(Double grade) {
            this.grade = grade;
            return this;
        }

        public SubmissionResponseBuilder feedback(String feedback) {
            this.feedback = feedback;
            return this;
        }

        public SubmissionResponse build() {
            return new SubmissionResponse(id, studentName, content, submittedAt, grade, feedback);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmissionResponse that = (SubmissionResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(studentName, that.studentName) && Objects.equals(content, that.content) && Objects.equals(submittedAt, that.submittedAt) && Objects.equals(grade, that.grade) && Objects.equals(feedback, that.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentName, content, submittedAt, grade, feedback);
    }

    // toString
    @Override
    public String toString() {
        return "SubmissionResponse{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", content='" + content + '\'' +
                ", submittedAt=" + submittedAt +
                ", grade=" + grade +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}

