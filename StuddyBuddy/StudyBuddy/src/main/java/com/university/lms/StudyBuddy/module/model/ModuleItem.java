package com.university.lms.StudyBuddy.module.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "module_items")
public class ModuleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private CourseModule module;

    @Enumerated(EnumType.STRING)
    private ModuleItemType type;

    @Column(columnDefinition = "TEXT")
    private String content;  // For text or URLs

    private String fileUrl;  // For files (handled in Step 10)

    private Integer position;  // order of items in this module

    // Constructors
    public ModuleItem() {
    }

    public ModuleItem(Long id, CourseModule module, ModuleItemType type, String content, String fileUrl, Integer position) {
        this.id = id;
        this.module = module;
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

    public CourseModule getModule() {
        return module;
    }

    public void setModule(CourseModule module) {
        this.module = module;
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
    public static ModuleItemBuilder builder() {
        return new ModuleItemBuilder();
    }

    public static class ModuleItemBuilder {
        private Long id;
        private CourseModule module;
        private ModuleItemType type;
        private String content;
        private String fileUrl;
        private Integer position;

        ModuleItemBuilder() {
        }

        public ModuleItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ModuleItemBuilder module(CourseModule module) {
            this.module = module;
            return this;
        }

        public ModuleItemBuilder type(ModuleItemType type) {
            this.type = type;
            return this;
        }

        public ModuleItemBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ModuleItemBuilder fileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }

        public ModuleItemBuilder position(Integer position) {
            this.position = position;
            return this;
        }

        public ModuleItem build() {
            return new ModuleItem(id, module, type, content, fileUrl, position);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleItem that = (ModuleItem) o;
        return Objects.equals(id, that.id) && Objects.equals(module, that.module) && type == that.type && Objects.equals(content, that.content) && Objects.equals(fileUrl, that.fileUrl) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, module, type, content, fileUrl, position);
    }

    // toString
    @Override
    public String toString() {
        return "ModuleItem{" +
                "id=" + id +
                ", module=" + module +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", position=" + position +
                '}';
    }
}
