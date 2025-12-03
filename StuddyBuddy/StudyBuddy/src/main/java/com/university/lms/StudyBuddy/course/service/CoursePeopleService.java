package com.university.lms.StudyBuddy.course.service;

import com.university.lms.StudyBuddy.course.dto.CoursePeopleResponse;
import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.course.repository.CourseRepository;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursePeopleService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CoursePeopleService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    private CoursePeopleResponse buildResponse(Course course) {
        CoursePeopleResponse.TeacherInfo teacherInfo = CoursePeopleResponse.TeacherInfo.builder()
                .id(course.getTeacher().getId())
                .fullName(course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName())
                .email(course.getTeacher().getEmail())
                .build();

        List<CoursePeopleResponse.StudentInfo> students = userRepository
                .findByStudentClass(course.getAssignedClass())
                .stream()
                .filter(u -> u.getRole() == Role.STUDENT)
                .map(u -> CoursePeopleResponse.StudentInfo.builder()
                        .id(u.getId())
                        .fullName(u.getFirstName() + " " + u.getLastName())
                        .email(u.getEmail())
                        .studentClass(u.getStudentClass())
                        .build())
                .toList();

        return CoursePeopleResponse.builder()
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .teacher(teacherInfo)
                .students(students)
                .build();
    }

    public CoursePeopleResponse getPeopleForStudent(Long courseId, User student) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (student.getStudentClass() == null ||
                !student.getStudentClass().equals(course.getAssignedClass())) {
            throw new IllegalArgumentException("You are not enrolled in this course");
        }

        return buildResponse(course);
    }

    public CoursePeopleResponse getPeopleForTeacher(Long courseId, User teacher) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("You are not the teacher of this course");
        }

        return buildResponse(course);
    }

    public CoursePeopleResponse getPeopleForAdmin(Long courseId, User admin) {
        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Not an admin");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        return buildResponse(course);
    }
}
