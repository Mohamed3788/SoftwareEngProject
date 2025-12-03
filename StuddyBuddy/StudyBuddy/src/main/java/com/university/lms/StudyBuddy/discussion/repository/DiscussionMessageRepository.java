package com.university.lms.StudyBuddy.discussion.repository;

import com.university.lms.StudyBuddy.discussion.model.DiscussionMessage;
import com.university.lms.StudyBuddy.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionMessageRepository extends JpaRepository<DiscussionMessage, Long> {

    List<DiscussionMessage> findByCourseOrderByTimestampAsc(Course course);
}
