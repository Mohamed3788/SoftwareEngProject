package com.university.lms.StudyBuddy.assignment.controller;

import com.university.lms.StudyBuddy.assignment.dto.StudentGradeItemResponse;
import com.university.lms.StudyBuddy.assignment.service.AssignmentService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/courses")
public class StudentCourseGradesController {

    private final AssignmentService assignmentService;

    public StudentCourseGradesController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/{courseId}/grades")
    public ResponseEntity<List<StudentGradeItemResponse>> getGrades(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(
                assignmentService.getGradesForStudentInCourse(courseId, student)
        );
    }
}
