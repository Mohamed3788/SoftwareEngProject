package com.university.lms.StudyBuddy.auth.controller;

import com.university.lms.StudyBuddy.auth.dto.AuthResponse;
import com.university.lms.StudyBuddy.auth.dto.LoginRequest;
import com.university.lms.StudyBuddy.auth.dto.SignupRequest;
import com.university.lms.StudyBuddy.auth.dto.UserResponse;
import com.university.lms.StudyBuddy.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest request) {
        UserResponse user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup-student")
    public ResponseEntity<UserResponse> signupStudent(@Valid @RequestBody SignupRequest request) {
        UserResponse user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup-teacher")
    public ResponseEntity<UserResponse> signupTeacher(@Valid @RequestBody SignupRequest request) {
        UserResponse user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
