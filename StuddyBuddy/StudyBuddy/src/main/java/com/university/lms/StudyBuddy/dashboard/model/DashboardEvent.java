package com.university.lms.StudyBuddy.dashboard.model;

import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.dashboard.model.EventType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "dashboard_events")
public class DashboardEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long courseId;

    private Long relatedObjectId; // assignmentId, submissionId, messageId...

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String message;

    private LocalDateTime timestamp;

    @ManyToOne
    private User targetUser; // student or teacher who sees the event

    // Constructors
    public DashboardEvent() {
    }

    public DashboardEvent(Long id, Long courseId, Long relatedObjectId, EventType eventType, String message, LocalDateTime timestamp, User targetUser) {
        this.id = id;
        this.courseId = courseId;
        this.relatedObjectId = relatedObjectId;
        this.eventType = eventType;
        this.message = message;
        this.timestamp = timestamp;
        this.targetUser = targetUser;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectId(Long relatedObjectId) {
        this.relatedObjectId = relatedObjectId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    // Builder pattern
    public static DashboardEventBuilder builder() {
        return new DashboardEventBuilder();
    }

    public static class DashboardEventBuilder {
        private Long id;
        private Long courseId;
        private Long relatedObjectId;
        private EventType eventType;
        private String message;
        private LocalDateTime timestamp;
        private User targetUser;

        DashboardEventBuilder() {
        }

        public DashboardEventBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DashboardEventBuilder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public DashboardEventBuilder relatedObjectId(Long relatedObjectId) {
            this.relatedObjectId = relatedObjectId;
            return this;
        }

        public DashboardEventBuilder eventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public DashboardEventBuilder message(String message) {
            this.message = message;
            return this;
        }

        public DashboardEventBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DashboardEventBuilder targetUser(User targetUser) {
            this.targetUser = targetUser;
            return this;
        }

        public DashboardEvent build() {
            return new DashboardEvent(id, courseId, relatedObjectId, eventType, message, timestamp, targetUser);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardEvent that = (DashboardEvent) o;
        return Objects.equals(id, that.id) && Objects.equals(courseId, that.courseId) && Objects.equals(relatedObjectId, that.relatedObjectId) && eventType == that.eventType && Objects.equals(message, that.message) && Objects.equals(timestamp, that.timestamp) && Objects.equals(targetUser, that.targetUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, relatedObjectId, eventType, message, timestamp, targetUser);
    }

    // toString
    @Override
    public String toString() {
        return "DashboardEvent{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", relatedObjectId=" + relatedObjectId +
                ", eventType=" + eventType +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", targetUser=" + targetUser +
                '}';
    }
}
