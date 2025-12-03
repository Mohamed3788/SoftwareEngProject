package com.university.lms.StudyBuddy.module.dto;

import com.university.lms.StudyBuddy.module.model.ModuleItemType;

import java.util.Objects;

public class AddModuleItemRequest {
    private ModuleItemType type;
    private String content;     // text or link
    private String fileUrl;     // filled in Step 10

    // Getters and Setters
    public ModuleItemType getType() {
        return type;
    }

    public void setType(ModuleItemType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddModuleItemRequest that = (AddModuleItemRequest) o;
        return type == that.type && Objects.equals(content, that.content) && Objects.equals(fileUrl, that.fileUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, content, fileUrl);
    }

    // toString
    @Override
    public String toString() {
        return "AddModuleItemRequest{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}

