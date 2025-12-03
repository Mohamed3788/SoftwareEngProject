package com.university.lms.StudyBuddy.assignment.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class StudentGradeItemResponse {

    private Long assignmentId;
    private String assignmentTitle;
    private String assignmentDescription;
    private LocalDateTime dueDate;

    private Long submissionId;
    private LocalDateTime submittedAt;
    private Double grade;
    private String feedback;

    private String status; // "NOT_SUBMITTED", "SUBMITTED", "GRADED"

    public StudentGradeItemResponse() {
    }

    public StudentGradeItemResponse(Long assignmentId, String assignmentTitle, String assignmentDescription, LocalDateTime dueDate, Long submissionId, LocalDateTime submittedAt, Double grade, String feedback, String status) {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.assignmentDescription = assignmentDescription;
        this.dueDate = dueDate;
        this.submissionId = submissionId;
        this.submittedAt = submittedAt;
        this.grade = grade;
        this.feedback = feedback;
        this.status = status;
    }

    // Getters and Setters
    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Builder pattern
    public static StudentGradeItemResponseBuilder builder() {
        return new StudentGradeItemResponseBuilder();
    }

    public static class StudentGradeItemResponseBuilder {
        private Long assignmentId;
        private String assignmentTitle;
        private String assignmentDescription;
        private LocalDateTime dueDate;
        private Long submissionId;
        private LocalDateTime submittedAt;
        private Double grade;
        private String feedback;
        private String status;

        StudentGradeItemResponseBuilder() {
        }

        public StudentGradeItemResponseBuilder assignmentId(Long assignmentId) {
            this.assignmentId = assignmentId;
            return this;
        }

        public StudentGradeItemResponseBuilder assignmentTitle(String assignmentTitle) {
            this.assignmentTitle = assignmentTitle;
            return this;
        }

        public StudentGradeItemResponseBuilder assignmentDescription(String assignmentDescription) {
            this.assignmentDescription = assignmentDescription;
            return this;
        }

        public StudentGradeItemResponseBuilder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public StudentGradeItemResponseBuilder submissionId(Long submissionId) {
            this.submissionId = submissionId;
            return this;
        }

        public StudentGradeItemResponseBuilder submittedAt(LocalDateTime submittedAt) {
            this.submittedAt = submittedAt;
            return this;
        }

        public StudentGradeItemResponseBuilder grade(Double grade) {
            this.grade = grade;
            return this;
        }

        public StudentGradeItemResponseBuilder feedback(String feedback) {
            this.feedback = feedback;
            return this;
        }

        public StudentGradeItemResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public StudentGradeItemResponse build() {
            return new StudentGradeItemResponse(assignmentId, assignmentTitle, assignmentDescription, dueDate, submissionId, submittedAt, grade, feedback, status);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGradeItemResponse that = (StudentGradeItemResponse) o;
        return Objects.equals(assignmentId, that.assignmentId) && Objects.equals(assignmentTitle, that.assignmentTitle) && Objects.equals(assignmentDescription, that.assignmentDescription) && Objects.equals(dueDate, that.dueDate) && Objects.equals(submissionId, that.submissionId) && Objects.equals(submittedAt, that.submittedAt) && Objects.equals(grade, that.grade) && Objects.equals(feedback, that.feedback) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId, assignmentTitle, assignmentDescription, dueDate, submissionId, submittedAt, grade, feedback, status);
    }

    // toString
    @Override
    public String toString() {
        return "StudentGradeItemResponse{" +
                "assignmentId=" + assignmentId +
                ", assignmentTitle='" + assignmentTitle + '\'' +
                ", assignmentDescription='" + assignmentDescription + '\'' +
                ", dueDate=" + dueDate +
                ", submissionId=" + submissionId +
                ", submittedAt=" + submittedAt +
                ", grade=" + grade +
                ", feedback='" + feedback + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
