package com.university.lms.StudyBuddy.todo.model;

import com.university.lms.StudyBuddy.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "todo_tasks")
public class ToDoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDate dueDate;

    private boolean completed;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User student;

    // Constructors
    public ToDoTask() {
    }

    public ToDoTask(Long id, String title, String description, LocalDate dueDate, boolean completed, User student) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.student = student;
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

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    // Builder pattern
    public static ToDoTaskBuilder builder() {
        return new ToDoTaskBuilder();
    }

    public static class ToDoTaskBuilder {
        private Long id;
        private String title;
        private String description;
        private LocalDate dueDate;
        private boolean completed;
        private User student;

        ToDoTaskBuilder() {
        }

        public ToDoTaskBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ToDoTaskBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ToDoTaskBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ToDoTaskBuilder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public ToDoTaskBuilder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public ToDoTaskBuilder student(User student) {
            this.student = student;
            return this;
        }

        public ToDoTask build() {
            return new ToDoTask(id, title, description, dueDate, completed, student);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoTask toDoTask = (ToDoTask) o;
        return completed == toDoTask.completed && Objects.equals(id, toDoTask.id) && Objects.equals(title, toDoTask.title) && Objects.equals(description, toDoTask.description) && Objects.equals(dueDate, toDoTask.dueDate) && Objects.equals(student, toDoTask.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate, completed, student);
    }

    // toString
    @Override
    public String toString() {
        return "ToDoTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                ", student=" + student +
                '}';
    }
}
