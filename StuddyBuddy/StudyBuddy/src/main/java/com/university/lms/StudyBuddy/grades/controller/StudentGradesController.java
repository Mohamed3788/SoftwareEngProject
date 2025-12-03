package com.university.lms.StudyBuddy.grades.controller;

import com.university.lms.StudyBuddy.assignment.dto.SubmissionResponse;
import com.university.lms.StudyBuddy.assignment.service.AssignmentService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/grades")
public class StudentGradesController {

    private final AssignmentService assignmentService;

    public StudentGradesController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public ResponseEntity<List<SubmissionResponse>> getGrades(
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(assignmentService.listGradesForStudent(student));
    }
}
