package com.university.lms.StudyBuddy.user.repository;

import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.StudentClass;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findByStudentClass(StudentClass studentClass);

    List<User> findByRole(Role role);

    long countByRole(Role role);
}
