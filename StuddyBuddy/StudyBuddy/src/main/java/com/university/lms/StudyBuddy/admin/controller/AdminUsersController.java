package com.university.lms.StudyBuddy.admin.controller;

import com.university.lms.StudyBuddy.user.dto.UserSummaryResponse;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUsersController {

    private final UserRepository userRepository;

    public AdminUsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ------------------------
    // LIST ALL STUDENTS
    // ------------------------
    @GetMapping("/students")
    public ResponseEntity<List<UserSummaryResponse>> listStudents() {
        List<UserSummaryResponse> res = userRepository.findByRole(Role.STUDENT)
                .stream()
                .map(u -> new UserSummaryResponse(
                        u.getId(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail()
                ))
                .toList();

        return ResponseEntity.ok(res);
    }

    // ------------------------
    // LIST ALL TEACHERS
    // Already implemented in AdminUserController, we keep consistency
    // ------------------------

    // ------------------------
    // LIST ALL ADMINS
    // ------------------------
    @GetMapping("/admins")
    public ResponseEntity<List<UserSummaryResponse>> listAdmins() {
        List<UserSummaryResponse> res = userRepository.findByRole(Role.ADMIN)
                .stream()
                .map(u -> new UserSummaryResponse(
                        u.getId(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail()
                ))
                .toList();

        return ResponseEntity.ok(res);
    }
}
