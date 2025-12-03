package com.university.lms.StudyBuddy.people.service;

import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.course.repository.CourseRepository;
import com.university.lms.StudyBuddy.people.dto.PeopleResponse;
import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public PeopleService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // STUDENT view
    public PeopleResponse getPeopleForStudent(Long courseId, User student) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Check student belongs to the class of this course
        if (!student.getStudentClass().equals(course.getAssignedClass())) {
            throw new IllegalStateException("You are not enrolled in this course");
        }

        List<User> students = userRepository.findByStudentClass(course.getAssignedClass());

        return buildResponse(course, students);
    }

    // TEACHER view
    public PeopleResponse getPeopleForTeacher(Long courseId, User teacher) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalStateException("You do not teach this course");
        }

        List<User> students = userRepository.findByStudentClass(course.getAssignedClass());

        return buildResponse(course, students);
    }

    private PeopleResponse buildResponse(Course course, List<User> students) {

        return PeopleResponse.builder()
                .teacher(
                        PeopleResponse.TeacherInfo.builder()
                                .id(course.getTeacher().getId())
                                .firstName(course.getTeacher().getFirstName())
                                .lastName(course.getTeacher().getLastName())
                                .email(course.getTeacher().getEmail())
                                .build()
                )
                .students(
                        students.stream().map(s ->
                                PeopleResponse.StudentInfo.builder()
                                        .id(s.getId())
                                        .firstName(s.getFirstName())
                                        .lastName(s.getLastName())
                                        .email(s.getEmail())
                                        .build()
                        ).toList()
                )
                .build();
    }
}
