package com.university.lms.StudyBuddy.discussion.controller;

import com.university.lms.StudyBuddy.discussion.dto.DiscussionMessageResponse;
import com.university.lms.StudyBuddy.discussion.service.DiscussionService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/discussion")
public class DiscussionRestController {

    private final DiscussionService discussionService;

    public DiscussionRestController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<List<DiscussionMessageResponse>> getMessages(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User requester
    ) {
        return ResponseEntity.ok(discussionService.loadHistory(courseId, requester));
    }
}
