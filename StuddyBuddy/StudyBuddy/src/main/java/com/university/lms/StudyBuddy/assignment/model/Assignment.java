package com.university.lms.StudyBuddy.assignment.model;

import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User createdBy;

    // Constructors
    public Assignment() {
    }

    public Assignment(Long id, String title, String description, LocalDateTime dueDate, Course course, User createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.course = course;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    // Builder pattern
    public static AssignmentBuilder builder() {
        return new AssignmentBuilder();
    }

    public static class AssignmentBuilder {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime dueDate;
        private Course course;
        private User createdBy;

        AssignmentBuilder() {
        }

        public AssignmentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AssignmentBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AssignmentBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AssignmentBuilder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public AssignmentBuilder course(Course course) {
            this.course = course;
            return this;
        }

        public AssignmentBuilder createdBy(User createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Assignment build() {
            return new Assignment(id, title, description, dueDate, course, createdBy);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(dueDate, that.dueDate) && Objects.equals(course, that.course) && Objects.equals(createdBy, that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate, course, createdBy);
    }

    // toString
    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", course=" + course +
                ", createdBy=" + createdBy +
                '}';
    }
}
