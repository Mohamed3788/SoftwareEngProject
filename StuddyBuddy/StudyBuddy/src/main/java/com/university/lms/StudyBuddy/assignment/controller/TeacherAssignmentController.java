package com.university.lms.StudyBuddy.assignment.controller;

import com.university.lms.StudyBuddy.assignment.dto.AssignmentResponse;
import com.university.lms.StudyBuddy.assignment.dto.CreateAssignmentRequest;
import com.university.lms.StudyBuddy.assignment.dto.GradeRequest;
import com.university.lms.StudyBuddy.assignment.dto.SubmissionResponse;
import com.university.lms.StudyBuddy.assignment.service.AssignmentService;
import com.university.lms.StudyBuddy.user.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/assignments")
public class TeacherAssignmentController {

    private final AssignmentService assignmentService;

    public TeacherAssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(
            @Valid @RequestBody CreateAssignmentRequest req,
            @AuthenticationPrincipal User teacher
    ) {
        return ResponseEntity.ok(assignmentService.createAssignment(req, teacher));
    }

    @PostMapping("/grade/{submissionId}")
    public ResponseEntity<SubmissionResponse> gradeSubmission(
            @PathVariable Long submissionId,
            @RequestBody GradeRequest req,
            @AuthenticationPrincipal User teacher
    ) {
        return ResponseEntity.ok(assignmentService.gradeSubmission(submissionId, req, teacher));
    }

    // TEACHER: list assignments for a course
    @GetMapping("/{courseId}")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentsForCourse(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User teacher
    ) {
        return ResponseEntity.ok(
                assignmentService.listAssignmentsForTeacher(courseId, teacher)
        );
    }

    // TEACHER: list submissions for an assignment
    @GetMapping("/submissions/{assignmentId}")
    public ResponseEntity<List<SubmissionResponse>> getSubmissions(
            @PathVariable Long assignmentId,
            @AuthenticationPrincipal User teacher
    ) {
        return ResponseEntity.ok(
                assignmentService.listSubmissionsForAssignment(assignmentId, teacher)
        );
    }

}
