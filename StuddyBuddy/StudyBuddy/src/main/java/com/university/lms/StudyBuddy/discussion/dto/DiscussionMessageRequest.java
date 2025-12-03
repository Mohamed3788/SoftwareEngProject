package com.university.lms.StudyBuddy.discussion.dto;

import java.util.Objects;

public class DiscussionMessageRequest {
    private Long courseId;
    private String content;

    // Getters and Setters
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DiscussionMessageRequest that = (DiscussionMessageRequest) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, content);
    }

    // toString
    @Override
    public String toString() {
        return "DiscussionMessageRequest{" +
                "courseId=" + courseId +
                ", content='" + content + '\'' +
                '}';
    }
}
