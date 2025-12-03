package com.university.lms.StudyBuddy.admin.controller;

import com.university.lms.StudyBuddy.admin.dto.AdminCourseResponse;
import com.university.lms.StudyBuddy.admin.dto.CreateCourseRequest;
import com.university.lms.StudyBuddy.admin.service.AdminCourseService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/courses")
public class AdminCourseController {

    private final AdminCourseService service;

    public AdminCourseController(AdminCourseService service) {
        this.service = service;
    }

    // CREATE COURSE
    @PostMapping
    public ResponseEntity<AdminCourseResponse> createCourse(
            @RequestBody CreateCourseRequest req,
            @AuthenticationPrincipal User admin
    ) {
        return ResponseEntity.ok(service.createCourse(req, admin));
    }

    // LIST ALL COURSES
    @GetMapping
    public ResponseEntity<List<AdminCourseResponse>> listCourses(
            @AuthenticationPrincipal User admin
    ) {
        return ResponseEntity.ok(service.listAllCourses(admin));
    }

    // UPDATE COURSE
    @PutMapping("/{id}")
    public ResponseEntity<AdminCourseResponse> updateCourse(
            @PathVariable Long id,
            @RequestBody CreateCourseRequest req,
            @AuthenticationPrincipal User admin
    ) {
        return ResponseEntity.ok(service.updateCourse(id, req, admin));
    }

    // DELETE COURSE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable Long id,
            @AuthenticationPrincipal User admin
    ) {
        service.deleteCourse(id, admin);
        return ResponseEntity.noContent().build();
    }
}
