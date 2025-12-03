package com.university.lms.StudyBuddy.dashboard.repository;

import com.university.lms.StudyBuddy.dashboard.model.DashboardEvent;
import com.university.lms.StudyBuddy.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DashboardEventRepository extends JpaRepository<DashboardEvent, Long> {

    @Query("SELECT e FROM DashboardEvent e WHERE e.targetUser = :user ORDER BY e.timestamp DESC")
    List<DashboardEvent> findLatestEventsForUser(User user, Pageable pageable);
}
