package com.university.lms.StudyBuddy.module.repository;

import com.university.lms.StudyBuddy.module.model.ModuleItem;
import com.university.lms.StudyBuddy.module.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleItemRepository extends JpaRepository<ModuleItem, Long> {

    List<ModuleItem> findByModuleOrderByPositionAsc(CourseModule module);
}
