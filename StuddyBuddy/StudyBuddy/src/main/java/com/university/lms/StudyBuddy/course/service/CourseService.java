package com.university.lms.StudyBuddy.course.service;

import com.university.lms.StudyBuddy.course.dto.CourseCreateRequest;
import com.university.lms.StudyBuddy.course.dto.CourseResponse;
import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.course.repository.CourseRepository;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    private CourseResponse map(Course c) {
        return CourseResponse.builder()
                .id(c.getId())
                .title(c.getTitle())
                .description(c.getDescription())
                .assignedClass(c.getAssignedClass())
                .teacherId(c.getTeacher().getId())
                .teacherName(c.getTeacher().getFirstName() + " " + c.getTeacher().getLastName())
                .build();
    }

    public CourseResponse createCourse(CourseCreateRequest req) {
        User teacher = userRepository.findById(req.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        if (teacher.getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("User is not a teacher");
        }

        Course course = Course.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .assignedClass(req.getAssignedClass())
                .teacher(teacher)
                .build();

        return map(courseRepository.save(course));
    }

    public List<CourseResponse> getCoursesForAdmin() {
        return courseRepository.findAll().stream().map(this::map).toList();
    }

    public List<CourseResponse> getCoursesForTeacher(User teacher) {
        return courseRepository.findByTeacher(teacher)
                .stream().map(this::map).toList();
    }

    public List<CourseResponse> getCoursesForStudent(User student) {
        return courseRepository.findByAssignedClass(student.getStudentClass())
                .stream().map(this::map).toList();
    }

    public CourseResponse getCourseById(Long id, User user) {

        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // ACCESS CONTROL
        switch (user.getRole()) {
            case STUDENT -> {
                if (!c.getAssignedClass().equals(user.getStudentClass())) {
                    throw new IllegalStateException("You cannot access this course");
                }
            }
            case TEACHER -> {
                if (!c.getTeacher().getId().equals(user.getId())) {
                    throw new IllegalStateException("You do not teach this course");
                }
            }
            case ADMIN -> {} // admin can access everything
        }

        return CourseResponse.builder()
                .id(c.getId())
                .title(c.getTitle())
                .description(c.getDescription())
                .teacherName(c.getTeacher().getFirstName() + " " + c.getTeacher().getLastName())
                .assignedClass(c.getAssignedClass())
                .build();
    }
}
