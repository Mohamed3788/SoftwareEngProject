package com.university.lms.StudyBuddy.course.controller;

import com.university.lms.StudyBuddy.course.dto.CoursePeopleResponse;
import com.university.lms.StudyBuddy.course.service.CoursePeopleService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CoursePeopleController {

    private final CoursePeopleService coursePeopleService;

    public CoursePeopleController(CoursePeopleService coursePeopleService) {
        this.coursePeopleService = coursePeopleService;
    }

    // STUDENT: people of a course
    @GetMapping("/student/courses/{courseId}/people")
    public ResponseEntity<CoursePeopleResponse> getPeopleForStudent(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(coursePeopleService.getPeopleForStudent(courseId, student));
    }

    // TEACHER: people of own course
    @GetMapping("/teacher/courses/{courseId}/people")
    public ResponseEntity<CoursePeopleResponse> getPeopleForTeacher(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User teacher
    ) {
        return ResponseEntity.ok(coursePeopleService.getPeopleForTeacher(courseId, teacher));
    }

    // ADMIN: people of any course
    @GetMapping("/admin/courses/{courseId}/people")
    public ResponseEntity<CoursePeopleResponse> getPeopleForAdmin(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User admin
    ) {
        return ResponseEntity.ok(coursePeopleService.getPeopleForAdmin(courseId, admin));
    }
}
