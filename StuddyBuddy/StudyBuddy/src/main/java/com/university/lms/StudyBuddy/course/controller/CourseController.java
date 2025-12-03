package com.university.lms.StudyBuddy.course.controller;

import com.university.lms.StudyBuddy.course.dto.CourseResponse;
import com.university.lms.StudyBuddy.course.service.CourseService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    private final CourseService courseService;
    
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // TEACHER: list own courses
    @GetMapping("/api/teacher/courses")
    public ResponseEntity<List<CourseResponse>> teacherCourses(
            @AuthenticationPrincipal User teacher
    ) {
        return ResponseEntity.ok(courseService.getCoursesForTeacher(teacher));
    }

    // STUDENT: list courses for class
    @GetMapping("/api/student/courses")
    public ResponseEntity<List<CourseResponse>> studentCourses(
            @AuthenticationPrincipal User student
    ) {
        return ResponseEntity.ok(courseService.getCoursesForStudent(student));
    }
    // COURSE DETAILS (shared for student & teacher)
    @GetMapping("/api/courses/{id}")
    public ResponseEntity<CourseResponse> getCourse(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(courseService.getCourseById(id, user));
    }
}
