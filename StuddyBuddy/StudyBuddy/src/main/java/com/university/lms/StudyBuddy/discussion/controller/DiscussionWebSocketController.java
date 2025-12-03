package com.university.lms.StudyBuddy.discussion.controller;

import com.university.lms.StudyBuddy.discussion.dto.DiscussionMessageRequest;
import com.university.lms.StudyBuddy.discussion.dto.DiscussionMessageResponse;
import com.university.lms.StudyBuddy.discussion.service.DiscussionService;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
public class DiscussionWebSocketController {

    private final DiscussionService discussionService;
    private final SimpMessagingTemplate messagingTemplate;

    public DiscussionWebSocketController(DiscussionService discussionService,
            SimpMessagingTemplate messagingTemplate) {
        this.discussionService = discussionService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/courses/{courseId}/send")
    public void sendMessage(
            @DestinationVariable Long courseId,
            DiscussionMessageRequest req,
            @AuthenticationPrincipal User sender) {
        System.out.println("=== WebSocket Message Received ===");
        System.out.println("Course ID: " + courseId);
        System.out.println("Content: " + req.getContent());
        System.out
                .println("Sender: " + (sender != null ? sender.getEmail() + " (ID: " + sender.getId() + ")" : "NULL"));

        if (sender == null) {
            System.err.println("ERROR: Sender is null! Authentication failed.");
            throw new IllegalArgumentException("User not authenticated");
        }

        DiscussionMessageResponse response = discussionService.saveMessage(courseId, req.getContent(), sender);
        System.out.println("Response created - Message ID: " + response.getId());

        // Broadcast to the specific course topic
        String destination = "/topic/courses/" + courseId;
        System.out.println("Broadcasting to: " + destination);
        messagingTemplate.convertAndSend(destination, response);
        System.out.println("✅ Message broadcast complete");
        System.out.println("=== End WebSocket Message ===");
    }
}
