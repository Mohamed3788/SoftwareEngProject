package com.university.lms.StudyBuddy.module.dto;

import com.university.lms.StudyBuddy.module.model.ModuleItemType;

import java.util.Objects;

public class ModuleItemResponse {
    private Long id;
    private ModuleItemType type;
    private String content;
    private String fileUrl;
    private Integer position;

    public ModuleItemResponse() {
    }

    public ModuleItemResponse(Long id, ModuleItemType type, String content, String fileUrl, Integer position) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.fileUrl = fileUrl;
        this.position = position;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    // Builder pattern
    public static ModuleItemResponseBuilder builder() {
        return new ModuleItemResponseBuilder();
    }

    public static class ModuleItemResponseBuilder {
        private Long id;
        private ModuleItemType type;
        private String content;
        private String fileUrl;
        private Integer position;

        ModuleItemResponseBuilder() {
        }

        public ModuleItemResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ModuleItemResponseBuilder type(ModuleItemType type) {
            this.type = type;
            return this;
        }

        public ModuleItemResponseBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ModuleItemResponseBuilder fileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }

        public ModuleItemResponseBuilder position(Integer position) {
            this.position = position;
            return this;
        }

        public ModuleItemResponse build() {
            return new ModuleItemResponse(id, type, content, fileUrl, position);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleItemResponse that = (ModuleItemResponse) o;
        return Objects.equals(id, that.id) && type == that.type && Objects.equals(content, that.content) && Objects.equals(fileUrl, that.fileUrl) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, content, fileUrl, position);
    }

    // toString
    @Override
    public String toString() {
        return "ModuleItemResponse{" +
                "id=" + id +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", position=" + position +
                '}';
    }
}
