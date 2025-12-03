package com.university.lms.StudyBuddy.auth.service;

import com.university.lms.StudyBuddy.auth.dto.AuthResponse;
import com.university.lms.StudyBuddy.auth.dto.LoginRequest;
import com.university.lms.StudyBuddy.auth.dto.SignupRequest;
import com.university.lms.StudyBuddy.auth.dto.UserResponse;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private static final String ADMIN_SECRET = "@dmin";

    public UserResponse register(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        if (request.getRole() == Role.STUDENT && request.getStudentClass() == null) {
            throw new IllegalArgumentException("Student class is required for student role");
        }

        if (request.getRole() == Role.ADMIN) {
            if (!StringUtils.hasText(request.getAdminPassword())
                    || !ADMIN_SECRET.equals(request.getAdminPassword())) {
                throw new IllegalArgumentException("Invalid adminPassword");
            }
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .studentClass(request.getRole() == Role.STUDENT ? request.getStudentClass() : null)
                .build();

        User saved = userRepository.save(user);

        return UserResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .role(saved.getRole())
                .studentClass(saved.getStudentClass())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .studentClass(user.getStudentClass())
                .build();
    }
}
