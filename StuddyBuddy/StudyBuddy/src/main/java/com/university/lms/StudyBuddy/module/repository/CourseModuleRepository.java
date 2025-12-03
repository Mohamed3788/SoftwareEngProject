package com.university.lms.StudyBuddy.module.repository;

import com.university.lms.StudyBuddy.module.model.CourseModule;
import com.university.lms.StudyBuddy.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {

    List<CourseModule> findByCourseOrderByIdAsc(Course course);
}
