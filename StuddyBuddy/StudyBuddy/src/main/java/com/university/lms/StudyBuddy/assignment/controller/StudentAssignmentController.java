package com.university.lms.StudyBuddy.assignment.controller;

import com.university.lms.StudyBuddy.assignment.dto.*;
import com.university.lms.StudyBuddy.assignment.service.AssignmentService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/student/assignments")
public class StudentAssignmentController {

    private final AssignmentService assignmentService;

    public StudentAssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    // ----------------------------------
    // LIST ALL ASSIGNMENTS FOR STUDENT
    // ----------------------------------
    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> listAssignments(
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(
                assignmentService.listAssignmentsForStudent(student)
        );
    }

    // ----------------------------------
    // SUBMIT ASSIGNMENT (TEXT OR LINK)
    // ----------------------------------
    @PostMapping("/submit/{assignmentId}")
    public ResponseEntity<SubmissionResponse> submitText(
            @PathVariable Long assignmentId,
            @RequestBody SubmitAssignmentRequest req,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(
                assignmentService.submitAssignment(assignmentId, req, student)
        );
    }

    // ----------------------------------
    // SUBMIT ASSIGNMENT AS FILE
    // ----------------------------------
    @PostMapping(
            value = "/submit-file/{assignmentId}",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<SubmissionResponse> submitFile(
            @PathVariable Long assignmentId,
            @RequestPart MultipartFile file,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(
                assignmentService.submitAssignmentFile(assignmentId, file, student)
        );
    }
}
