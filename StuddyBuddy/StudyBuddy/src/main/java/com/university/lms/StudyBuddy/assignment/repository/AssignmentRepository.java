package com.university.lms.StudyBuddy.assignment.repository;

import com.university.lms.StudyBuddy.assignment.model.Assignment;
import com.university.lms.StudyBuddy.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByCourse(Course course);
}
