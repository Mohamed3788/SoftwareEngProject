package com.university.lms.StudyBuddy.todo.controller;

import com.university.lms.StudyBuddy.todo.dto.ToDoTaskRequest;
import com.university.lms.StudyBuddy.todo.dto.ToDoTaskResponse;
import com.university.lms.StudyBuddy.todo.service.ToDoTaskService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/todo")
public class ToDoTaskController {

    private final ToDoTaskService service;

    public ToDoTaskController(ToDoTaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ToDoTaskResponse> create(
            @RequestBody ToDoTaskRequest req,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(service.create(req, student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoTaskResponse> update(
            @PathVariable Long id,
            @RequestBody ToDoTaskRequest req,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(service.update(id, req, student));
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<ToDoTaskResponse> toggle(
            @PathVariable Long id,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(service.toggleComplete(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User student
    ) {
        service.delete(id, student);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ToDoTaskResponse>> list(
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(service.list(student));
    }
}
