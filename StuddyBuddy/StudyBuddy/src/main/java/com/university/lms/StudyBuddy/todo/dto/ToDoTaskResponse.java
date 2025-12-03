package com.university.lms.StudyBuddy.todo.dto;

import java.time.LocalDate;
import java.util.Objects;

public class ToDoTaskResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;

    public ToDoTaskResponse() {
    }

    public ToDoTaskResponse(Long id, String title, String description, LocalDate dueDate, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Builder pattern
    public static ToDoTaskResponseBuilder builder() {
        return new ToDoTaskResponseBuilder();
    }

    public static class ToDoTaskResponseBuilder {
        private Long id;
        private String title;
        private String description;
        private LocalDate dueDate;
        private boolean completed;

        ToDoTaskResponseBuilder() {
        }

        public ToDoTaskResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ToDoTaskResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ToDoTaskResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ToDoTaskResponseBuilder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public ToDoTaskResponseBuilder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public ToDoTaskResponse build() {
            return new ToDoTaskResponse(id, title, description, dueDate, completed);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoTaskResponse that = (ToDoTaskResponse) o;
        return completed == that.completed && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate, completed);
    }

    // toString
    @Override
    public String toString() {
        return "ToDoTaskResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                '}';
    }
}
