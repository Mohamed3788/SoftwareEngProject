package com.university.lms.StudyBuddy.module.dto;

import java.util.List;
import java.util.Objects;

public class CourseModuleResponse {
    private Long id;
    private String title;
    private List<ModuleItemResponse> items;

    public CourseModuleResponse() {
    }

    public CourseModuleResponse(Long id, String title, List<ModuleItemResponse> items) {
        this.id = id;
        this.title = title;
        this.items = items;
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

    public List<ModuleItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ModuleItemResponse> items) {
        this.items = items;
    }

    // Builder pattern
    public static CourseModuleResponseBuilder builder() {
        return new CourseModuleResponseBuilder();
    }

    public static class CourseModuleResponseBuilder {
        private Long id;
        private String title;
        private List<ModuleItemResponse> items;

        CourseModuleResponseBuilder() {
        }

        public CourseModuleResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseModuleResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseModuleResponseBuilder items(List<ModuleItemResponse> items) {
            this.items = items;
            return this;
        }

        public CourseModuleResponse build() {
            return new CourseModuleResponse(id, title, items);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseModuleResponse that = (CourseModuleResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, items);
    }

    // toString
    @Override
    public String toString() {
        return "CourseModuleResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}
