package com.university.lms.StudyBuddy.admin.service;

import com.university.lms.StudyBuddy.admin.dto.AdminCourseResponse;
import com.university.lms.StudyBuddy.admin.dto.CreateCourseRequest;
import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.course.repository.CourseRepository;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public AdminCourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // 1. CREATE COURSE
    public AdminCourseResponse createCourse(CreateCourseRequest req, User admin) {

        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can create courses");
        }

        User teacher = userRepository.findById(req.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        if (teacher.getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("This user is not a teacher");
        }

        Course course = Course.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .teacher(teacher)
                .assignedClass(req.getAssignedClass())
                .build();

        course = courseRepository.save(course);

        return toResponse(course);
    }

    // 2. LIST ALL COURSES
    public List<AdminCourseResponse> listAllCourses(User admin) {
        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can view all courses");
        }

        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // 3. UPDATE COURSE
    public AdminCourseResponse updateCourse(Long id, CreateCourseRequest req, User admin) {
        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can update courses");
        }

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        User teacher = userRepository.findById(req.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        if (teacher.getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("This user is not a teacher");
        }

        course.setTitle(req.getTitle());
        course.setDescription(req.getDescription());
        course.setAssignedClass(req.getAssignedClass());
        course.setTeacher(teacher);

        course = courseRepository.save(course);

        return toResponse(course);
    }

    // 4. DELETE COURSE
    public void deleteCourse(Long id, User admin) {
        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only admins can delete courses");
        }

        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Course not found");
        }

        courseRepository.deleteById(id);
    }

    // Mapping
    private AdminCourseResponse toResponse(Course c) {
        return AdminCourseResponse.builder()
                .id(c.getId())
                .title(c.getTitle())
                .description(c.getDescription())
                .teacherId(c.getTeacher().getId())
                .teacherName(c.getTeacher().getFirstName() + " " + c.getTeacher().getLastName())
                .assignedClass(c.getAssignedClass())
                .build();
    }
}
