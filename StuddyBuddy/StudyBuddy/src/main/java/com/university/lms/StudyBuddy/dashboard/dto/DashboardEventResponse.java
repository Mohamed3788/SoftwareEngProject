package com.university.lms.StudyBuddy.dashboard.dto;

import com.university.lms.StudyBuddy.dashboard.model.EventType;

import java.time.LocalDateTime;
import java.util.Objects;

public class DashboardEventResponse {
    private Long id;
    private Long courseId;
    private Long relatedObjectId;
    private EventType eventType;
    private String message;
    private LocalDateTime timestamp;

    public DashboardEventResponse() {
    }

    public DashboardEventResponse(Long id, Long courseId, Long relatedObjectId, EventType eventType, String message, LocalDateTime timestamp) {
        this.id = id;
        this.courseId = courseId;
        this.relatedObjectId = relatedObjectId;
        this.eventType = eventType;
        this.message = message;
        this.timestamp = timestamp;
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

    // Builder pattern
    public static DashboardEventResponseBuilder builder() {
        return new DashboardEventResponseBuilder();
    }

    public static class DashboardEventResponseBuilder {
        private Long id;
        private Long courseId;
        private Long relatedObjectId;
        private EventType eventType;
        private String message;
        private LocalDateTime timestamp;

        DashboardEventResponseBuilder() {
        }

        public DashboardEventResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DashboardEventResponseBuilder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public DashboardEventResponseBuilder relatedObjectId(Long relatedObjectId) {
            this.relatedObjectId = relatedObjectId;
            return this;
        }

        public DashboardEventResponseBuilder eventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public DashboardEventResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public DashboardEventResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DashboardEventResponse build() {
            return new DashboardEventResponse(id, courseId, relatedObjectId, eventType, message, timestamp);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardEventResponse that = (DashboardEventResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(courseId, that.courseId) && Objects.equals(relatedObjectId, that.relatedObjectId) && eventType == that.eventType && Objects.equals(message, that.message) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, relatedObjectId, eventType, message, timestamp);
    }

    // toString
    @Override
    public String toString() {
        return "DashboardEventResponse{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", relatedObjectId=" + relatedObjectId +
                ", eventType=" + eventType +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
