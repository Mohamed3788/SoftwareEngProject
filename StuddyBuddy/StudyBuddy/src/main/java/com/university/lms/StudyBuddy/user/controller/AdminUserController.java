package com.university.lms.StudyBuddy.user.controller;

import com.university.lms.StudyBuddy.user.dto.TeacherSummaryResponse;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private final UserRepository userRepository;

    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherSummaryResponse>> listTeachers() {
        List<User> teachers = userRepository.findByRole(Role.TEACHER);

        List<TeacherSummaryResponse> result = teachers.stream()
                .map(t -> TeacherSummaryResponse.builder()
                        .id(t.getId())
                        .firstName(t.getFirstName())
                        .lastName(t.getLastName())
                        .email(t.getEmail())
                        .build())
                .toList();

        return ResponseEntity.ok(result);
    }
}
