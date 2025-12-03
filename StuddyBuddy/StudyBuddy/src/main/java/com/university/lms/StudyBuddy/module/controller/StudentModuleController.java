package com.university.lms.StudyBuddy.module.controller;

import com.university.lms.StudyBuddy.module.dto.CourseModuleResponse;
import com.university.lms.StudyBuddy.module.service.CourseModuleService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/modules")
public class StudentModuleController {

    private final CourseModuleService service;

    public StudentModuleController(CourseModuleService service) {
        this.service = service;
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<List<CourseModuleResponse>> viewModules(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(service.studentViewModules(courseId, student));
    }
}
