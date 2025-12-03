package com.university.lms.StudyBuddy.course.model;

import com.university.lms.StudyBuddy.user.model.StudentClass;
import com.university.lms.StudyBuddy.user.model.User;
import jakarta.persistence.*;
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentClass assignedClass;

    // teacher assigned to this course
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    // Constructors
    public Course() {
    }

    public Course(Long id, String title, String description, StudentClass assignedClass, User teacher) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedClass = assignedClass;
        this.teacher = teacher;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StudentClass getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(StudentClass assignedClass) {
        this.assignedClass = assignedClass;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    // Builder pattern
    public static CourseBuilder builder() {
        return new CourseBuilder();
    }

    public static class CourseBuilder {
        private Long id;
        private String title;
        private String description;
        private StudentClass assignedClass;
        private User teacher;

        CourseBuilder() {
        }

        public CourseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseBuilder assignedClass(StudentClass assignedClass) {
            this.assignedClass = assignedClass;
            return this;
        }

        public CourseBuilder teacher(User teacher) {
            this.teacher = teacher;
            return this;
        }

        public Course build() {
            return new Course(id, title, description, assignedClass, teacher);
        }
    }
}