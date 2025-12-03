package com.university.lms.StudyBuddy.assignment.repository;

import com.university.lms.StudyBuddy.assignment.model.AssignmentSubmission;
import com.university.lms.StudyBuddy.assignment.model.Assignment;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {

    Optional<AssignmentSubmission> findByAssignmentAndStudent(Assignment assignment, User student);

    List<AssignmentSubmission> findByAssignment(Assignment assignment);

    List<AssignmentSubmission> findByStudent(User student);

}
