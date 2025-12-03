package com.university.lms.StudyBuddy.discussion.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class DiscussionMessageResponse {
    private Long id;
    private String senderName;
    private Long senderId;
    private String content;
    private LocalDateTime timestamp;

    public DiscussionMessageResponse() {
    }

    public DiscussionMessageResponse(Long id, String senderName, Long senderId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.senderName = senderName;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Builder pattern
    public static DiscussionMessageResponseBuilder builder() {
        return new DiscussionMessageResponseBuilder();
    }

    public static class DiscussionMessageResponseBuilder {
        private Long id;
        private String senderName;
        private Long senderId;
        private String content;
        private LocalDateTime timestamp;

        DiscussionMessageResponseBuilder() {
        }

        public DiscussionMessageResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DiscussionMessageResponseBuilder senderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        public DiscussionMessageResponseBuilder senderId(Long senderId) {
            this.senderId = senderId;
            return this;
        }

        public DiscussionMessageResponseBuilder content(String content) {
            this.content = content;
            return this;
        }

        public DiscussionMessageResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DiscussionMessageResponse build() {
            return new DiscussionMessageResponse(id, senderName, senderId, content, timestamp);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionMessageResponse that = (DiscussionMessageResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(senderName, that.senderName) && Objects.equals(senderId, that.senderId) && Objects.equals(content, that.content) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderName, senderId, content, timestamp);
    }

    // toString
    @Override
    public String toString() {
        return "DiscussionMessageResponse{" +
                "id=" + id +
                ", senderName='" + senderName + '\'' +
                ", senderId=" + senderId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
