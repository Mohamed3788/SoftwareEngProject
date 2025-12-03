package com.university.lms.StudyBuddy.course.repository;

import com.university.lms.StudyBuddy.course.model.Course;
import com.university.lms.StudyBuddy.user.model.StudentClass;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTeacher(User teacher);

    List<Course> findByAssignedClass(StudentClass assignedClass);
}
