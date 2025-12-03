package com.university.lms.StudyBuddy.admin.service;

import com.university.lms.StudyBuddy.admin.dto.AdminDashboardResponse;
import com.university.lms.StudyBuddy.assignment.repository.AssignmentSubmissionRepository;
import com.university.lms.StudyBuddy.course.repository.CourseRepository;
import com.university.lms.StudyBuddy.discussion.repository.DiscussionMessageRepository;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final DiscussionMessageRepository discussionRepository;

    public AdminDashboardService(UserRepository userRepository, CourseRepository courseRepository, AssignmentSubmissionRepository submissionRepository, DiscussionMessageRepository discussionRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.submissionRepository = submissionRepository;
        this.discussionRepository = discussionRepository;
    }

    public AdminDashboardResponse buildDashboard(User admin) {
        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Not an admin");
        }

        long totalStudents = userRepository.countByRole(Role.STUDENT);
        long totalTeachers = userRepository.countByRole(Role.TEACHER);
        long totalAdmins = userRepository.countByRole(Role.ADMIN);

        long totalCourses = courseRepository.count();

        long totalSubmissions = submissionRepository.count();
        long totalGradedSubmissions = submissionRepository.findAll()
                .stream()
                .filter(s -> Objects.nonNull(s.getGrade()))
                .count();

        long totalDiscussionMessages = discussionRepository.count();

        return AdminDashboardResponse.builder()
                .totalStudents(totalStudents)
                .totalTeachers(totalTeachers)
                .totalAdmins(totalAdmins)
                .totalCourses(totalCourses)
                .totalSubmissions(totalSubmissions)
                .totalGradedSubmissions(totalGradedSubmissions)
                .totalDiscussionMessages(totalDiscussionMessages)
                .build();
    }
}
