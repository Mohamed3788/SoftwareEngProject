package com.university.lms.StudyBuddy.assignment.model;

import com.university.lms.StudyBuddy.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "assignment_submissions")
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Assignment assignment;

    @ManyToOne
    private User student;

    private LocalDateTime submittedAt;

    @Column(columnDefinition = "TEXT")
    private String content;  // can be text OR a link pointing to uploaded file

    private Double grade;
    private String feedback;

    // Constructors
    public AssignmentSubmission() {
    }

    public AssignmentSubmission(Long id, Assignment assignment, User student, LocalDateTime submittedAt, String content, Double grade, String feedback) {
        this.id = id;
        this.assignment = assignment;
        this.student = student;
        this.submittedAt = submittedAt;
        this.content = content;
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

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    public static AssignmentSubmissionBuilder builder() {
        return new AssignmentSubmissionBuilder();
    }

    public static class AssignmentSubmissionBuilder {
        private Long id;
        private Assignment assignment;
        private User student;
        private LocalDateTime submittedAt;
        private String content;
        private Double grade;
        private String feedback;

        AssignmentSubmissionBuilder() {
        }

        public AssignmentSubmissionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AssignmentSubmissionBuilder assignment(Assignment assignment) {
            this.assignment = assignment;
            return this;
        }

        public AssignmentSubmissionBuilder student(User student) {
            this.student = student;
            return this;
        }

        public AssignmentSubmissionBuilder submittedAt(LocalDateTime submittedAt) {
            this.submittedAt = submittedAt;
            return this;
        }

        public AssignmentSubmissionBuilder content(String content) {
            this.content = content;
            return this;
        }

        public AssignmentSubmissionBuilder grade(Double grade) {
            this.grade = grade;
            return this;
        }

        public AssignmentSubmissionBuilder feedback(String feedback) {
            this.feedback = feedback;
            return this;
        }

        public AssignmentSubmission build() {
            return new AssignmentSubmission(id, assignment, student, submittedAt, content, grade, feedback);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentSubmission that = (AssignmentSubmission) o;
        return Objects.equals(id, that.id) && Objects.equals(assignment, that.assignment) && Objects.equals(student, that.student) && Objects.equals(submittedAt, that.submittedAt) && Objects.equals(content, that.content) && Objects.equals(grade, that.grade) && Objects.equals(feedback, that.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assignment, student, submittedAt, content, grade, feedback);
    }

    // toString
    @Override
    public String toString() {
        return "AssignmentSubmission{" +
                "id=" + id +
                ", assignment=" + assignment +
                ", student=" + student +
                ", submittedAt=" + submittedAt +
                ", content='" + content + '\'' +
                ", grade=" + grade +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
