package com.university.lms.StudyBuddy.discussion.model;

import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "discussion_messages")
public class DiscussionMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User sender;

    private LocalDateTime timestamp;

    // Constructors
    public DiscussionMessage() {
    }

    public DiscussionMessage(Long id, String content, Course course, User sender, LocalDateTime timestamp) {
        this.id = id;
        this.content = content;
        this.course = course;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Builder pattern
    public static DiscussionMessageBuilder builder() {
        return new DiscussionMessageBuilder();
    }

    public static class DiscussionMessageBuilder {
        private Long id;
        private String content;
        private Course course;
        private User sender;
        private LocalDateTime timestamp;

        DiscussionMessageBuilder() {
        }

        public DiscussionMessageBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DiscussionMessageBuilder content(String content) {
            this.content = content;
            return this;
        }

        public DiscussionMessageBuilder course(Course course) {
            this.course = course;
            return this;
        }

        public DiscussionMessageBuilder sender(User sender) {
            this.sender = sender;
            return this;
        }

        public DiscussionMessageBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DiscussionMessage build() {
            return new DiscussionMessage(id, content, course, sender, timestamp);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionMessage that = (DiscussionMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(course, that.course) && Objects.equals(sender, that.sender) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, course, sender, timestamp);
    }

    // toString
    @Override
    public String toString() {
        return "DiscussionMessage{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", course=" + course +
                ", sender=" + sender +
                ", timestamp=" + timestamp +
                '}';
    }
}
