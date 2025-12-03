package com.university.lms.StudyBuddy.todo.repository;

import com.university.lms.StudyBuddy.todo.model.ToDoTask;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoTaskRepository extends JpaRepository<ToDoTask, Long> {

    List<ToDoTask> findByStudentOrderByDueDateAsc(User student);
}
