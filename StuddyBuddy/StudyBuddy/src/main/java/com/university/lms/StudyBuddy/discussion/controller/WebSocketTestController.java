package com.university.lms.StudyBuddy.discussion.controller;

import com.university.lms.StudyBuddy.discussion.dto.DiscussionMessageResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/test")
public class WebSocketTestController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketTestController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/broadcast/{courseId}")
    public String testBroadcast(@PathVariable Long courseId, @RequestBody String message) {
        System.out.println("=== TEST BROADCAST ===");
        System.out.println("Course ID: " + courseId);
        System.out.println("Message: " + message);

        DiscussionMessageResponse testMessage = DiscussionMessageResponse.builder()
                .id(999L)
                .content("TEST: " + message)
                .senderId(1L)
                .senderName("Test User")
                .timestamp(LocalDateTime.now())
                .build();

        String destination = "/topic/courses/" + courseId;
        System.out.println("Broadcasting to: " + destination);
        messagingTemplate.convertAndSend(destination, testMessage);
        System.out.println("✅ Test broadcast complete");

        return "Broadcast sent to " + destination;
    }
}
