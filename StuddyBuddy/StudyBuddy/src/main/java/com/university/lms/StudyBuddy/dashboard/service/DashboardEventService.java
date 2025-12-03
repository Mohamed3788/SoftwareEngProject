package com.university.lms.StudyBuddy.dashboard.service;

import com.university.lms.StudyBuddy.dashboard.dto.DashboardEventResponse;
import com.university.lms.StudyBuddy.dashboard.model.DashboardEvent;
import com.university.lms.StudyBuddy.dashboard.model.EventType;
import com.university.lms.StudyBuddy.dashboard.repository.DashboardEventRepository;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardEventService {

    private final DashboardEventRepository eventRepository;

    public DashboardEventService(DashboardEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Push an event to a specific user.
     */
    public void pushEventToUser(Long courseId,
                                Long relatedObjectId,
                                User targetUser,
                                EventType eventType,
                                String message) {

        DashboardEvent event = DashboardEvent.builder()
                .courseId(courseId)
                .relatedObjectId(relatedObjectId)
                .targetUser(targetUser)
                .eventType(eventType)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        eventRepository.save(event);
    }

    /**
     * Fetch the last N events for a user.
     */
    public List<DashboardEventResponse> getLastEvents(User user, int limit) {

        List<DashboardEvent> events = eventRepository.findLatestEventsForUser(
                user, PageRequest.of(0, limit)
        );

        return events.stream()
                .map(e -> DashboardEventResponse.builder()
                        .id(e.getId())
                        .courseId(e.getCourseId())
                        .relatedObjectId(e.getRelatedObjectId())
                        .eventType(e.getEventType())
                        .message(e.getMessage())
                        .timestamp(e.getTimestamp())
                        .build()
                )
                .toList();
    }

    // Older compatibility method
    public List<DashboardEventResponse> getEventsForUser(User user) {
        return getLastEvents(user, 10);
    }
}
