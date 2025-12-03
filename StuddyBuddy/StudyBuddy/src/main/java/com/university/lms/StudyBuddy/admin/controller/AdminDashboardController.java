package com.university.lms.StudyBuddy.admin.controller;

import com.university.lms.StudyBuddy.admin.dto.AdminDashboardResponse;
import com.university.lms.StudyBuddy.admin.service.AdminDashboardService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<AdminDashboardResponse> getDashboard(
            @AuthenticationPrincipal User admin
    ) {
        return ResponseEntity.ok(dashboardService.buildDashboard(admin));
    }
}
