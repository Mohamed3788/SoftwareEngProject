package com.university.lms.StudyBuddy.grades.controller;

import com.university.lms.StudyBuddy.assignment.dto.SubmissionResponse;
import com.university.lms.StudyBuddy.assignment.service.AssignmentService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/courses")
public class TeacherGradesController {

    private final AssignmentService assignmentService;

    public TeacherGradesController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/{courseId}/grades")
    public ResponseEntity<List<SubmissionResponse>> getGrades(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User teacher
    ) {
        return ResponseEntity.ok(
                assignmentService.listGradesForCourse(courseId, teacher)
        );
    }
}
