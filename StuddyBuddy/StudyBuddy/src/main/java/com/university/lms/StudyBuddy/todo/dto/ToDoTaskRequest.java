package com.university.lms.StudyBuddy.todo.dto;

import java.time.LocalDate;
import java.util.Objects;

public class ToDoTaskRequest {
    private String title;
    private String description;
    private LocalDate dueDate;

    // Getters and Setters
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoTaskRequest that = (ToDoTaskRequest) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, dueDate);
    }

    // toString
    @Override
    public String toString() {
        return "ToDoTaskRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
