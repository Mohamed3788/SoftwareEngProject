package com.university.lms.StudyBuddy.dashboard.controller;

import com.university.lms.StudyBuddy.dashboard.dto.DashboardEventResponse;
import com.university.lms.StudyBuddy.dashboard.service.DashboardEventService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardEventService dashboardEventService;

    public DashboardController(DashboardEventService dashboardEventService) {
        this.dashboardEventService = dashboardEventService;
    }

    @GetMapping
    public ResponseEntity<List<DashboardEventResponse>> getDashboard(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                dashboardEventService.getLastEvents(user, 10)
        );
    }
}
