package com.university.lms.StudyBuddy.todo.service;

import com.university.lms.StudyBuddy.todo.dto.ToDoTaskRequest;
import com.university.lms.StudyBuddy.todo.dto.ToDoTaskResponse;
import com.university.lms.StudyBuddy.todo.model.ToDoTask;
import com.university.lms.StudyBuddy.todo.repository.ToDoTaskRepository;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoTaskService {

    private final ToDoTaskRepository repository;

    public ToDoTaskService(ToDoTaskRepository repository) {
        this.repository = repository;
    }

    private void ensureStudent(User user) {
        if (user.getRole() != Role.STUDENT) {
            throw new IllegalArgumentException("Only students have a To-Do list");
        }
    }

    private ToDoTaskResponse map(ToDoTask task) {
        return ToDoTaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .completed(task.isCompleted())
                .build();
    }

    // CREATE TASK
    public ToDoTaskResponse create(ToDoTaskRequest req, User student) {
        ensureStudent(student);

        ToDoTask task = ToDoTask.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .dueDate(req.getDueDate())
                .completed(false)
                .student(student)
                .build();

        task = repository.save(task);
        return map(task);
    }

    // UPDATE TASK
    public ToDoTaskResponse update(Long id, ToDoTaskRequest req, User student) {
        ensureStudent(student);

        ToDoTask task = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getStudent().getId().equals(student.getId())) {
            throw new IllegalArgumentException("Not your task");
        }

        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setDueDate(req.getDueDate());

        task = repository.save(task);
        return map(task);
    }

    // TOGGLE COMPLETE
    public ToDoTaskResponse toggleComplete(Long id, User student) {
        ensureStudent(student);

        ToDoTask task = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getStudent().getId().equals(student.getId())) {
            throw new IllegalArgumentException("Not your task");
        }

        task.setCompleted(!task.isCompleted());
        task = repository.save(task);
        return map(task);
    }

    // DELETE
    public void delete(Long id, User student) {
        ensureStudent(student);

        ToDoTask task = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getStudent().getId().equals(student.getId())) {
            throw new IllegalArgumentException("Not your task");
        }

        repository.delete(task);
    }

    // LIST TASKS
    public List<ToDoTaskResponse> list(User student) {
        ensureStudent(student);

        return repository.findByStudentOrderByDueDateAsc(student)
                .stream()
                .map(this::map)
                .toList();
    }
}
