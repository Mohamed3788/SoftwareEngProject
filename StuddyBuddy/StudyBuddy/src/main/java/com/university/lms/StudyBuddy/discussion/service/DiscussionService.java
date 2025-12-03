package com.university.lms.StudyBuddy.discussion.service;

import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.course.repository.CourseRepository;
import com.university.lms.StudyBuddy.dashboard.model.EventType;
import com.university.lms.StudyBuddy.dashboard.service.DashboardEventService;
import com.university.lms.StudyBuddy.discussion.dto.DiscussionMessageResponse;
import com.university.lms.StudyBuddy.discussion.model.DiscussionMessage;
import com.university.lms.StudyBuddy.discussion.repository.DiscussionMessageRepository;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscussionService {

    private final DiscussionMessageRepository messageRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final DashboardEventService eventService;

    public DiscussionService(DiscussionMessageRepository messageRepository, CourseRepository courseRepository,
            UserRepository userRepository, DashboardEventService eventService) {
        this.messageRepository = messageRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
    }

    public DiscussionMessageResponse saveMessage(Long courseId, String content, User sender) {
        System.out.println("DiscussionService.saveMessage - Course ID: " + courseId + ", Sender: " + sender.getEmail());

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Permissions
        if (sender.getRole() == Role.STUDENT &&
                sender.getStudentClass() != course.getAssignedClass()) {
            throw new IllegalArgumentException("You are not part of this course");
        }

        if (sender.getRole() == Role.TEACHER &&
                !course.getTeacher().getId().equals(sender.getId())) {
            throw new IllegalArgumentException("You are not this course's teacher");
        }

        DiscussionMessage msg = DiscussionMessage.builder()
                .course(course)
                .sender(sender)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();

        msg = messageRepository.save(msg);
        System.out.println("Message saved to DB - ID: " + msg.getId());

        if (sender.getRole() == Role.TEACHER) {
            List<User> students = userRepository.findByStudentClass(course.getAssignedClass());

            DiscussionMessage finalMsg = msg;
            students.forEach(s -> eventService.pushEventToUser(
                    course.getId(),
                    finalMsg.getId(),
                    s,
                    EventType.NEW_DISCUSSION_MESSAGE,
                    "New message in " + course.getTitle()));
        } else {
            eventService.pushEventToUser(
                    course.getId(),
                    msg.getId(),
                    course.getTeacher(),
                    EventType.NEW_DISCUSSION_MESSAGE,
                    "New student message in " + course.getTitle());
        }

        DiscussionMessageResponse response = DiscussionMessageResponse.builder()
                .id(msg.getId())
                .content(msg.getContent())
                .senderId(sender.getId())
                .senderName(sender.getFirstName() + " " + sender.getLastName())
                .timestamp(msg.getTimestamp())
                .build();

        System.out.println("Returning response: " + response);
        return response;
    }

    public List<DiscussionMessageResponse> loadHistory(Long courseId, User requester) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (requester.getRole() == Role.STUDENT &&
                requester.getStudentClass() != course.getAssignedClass()) {
            throw new IllegalArgumentException("Access denied");
        }

        if (requester.getRole() == Role.TEACHER &&
                !course.getTeacher().getId().equals(requester.getId())) {
            throw new IllegalArgumentException("Access denied");
        }

        return messageRepository.findByCourseOrderByTimestampAsc(course)
                .stream()
                .map(m -> DiscussionMessageResponse.builder()
                        .id(m.getId())
                        .content(m.getContent())
                        .senderId(m.getSender().getId())
                        .senderName(m.getSender().getFirstName() + " " + m.getSender().getLastName())
                        .timestamp(m.getTimestamp())
                        .build())
                .toList();
    }
}
