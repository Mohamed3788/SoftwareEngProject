package com.university.lms.StudyBuddy.module.model;

import com.university.lms.StudyBuddy.course.model.Course;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "course_modules")
public class CourseModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<ModuleItem> items;

    // Constructors
    public CourseModule() {
    }

    public CourseModule(Long id, String title, Course course, List<ModuleItem> items) {
        this.id = id;
        this.title = title;
        this.course = course;
        this.items = items;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<ModuleItem> getItems() {
        return items;
    }

    public void setItems(List<ModuleItem> items) {
        this.items = items;
    }

    // Builder pattern
    public static CourseModuleBuilder builder() {
        return new CourseModuleBuilder();
    }

    public static class CourseModuleBuilder {
        private Long id;
        private String title;
        private Course course;
        private List<ModuleItem> items;

        CourseModuleBuilder() {
        }

        public CourseModuleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseModuleBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseModuleBuilder course(Course course) {
            this.course = course;
            return this;
        }

        public CourseModuleBuilder items(List<ModuleItem> items) {
            this.items = items;
            return this;
        }

        public CourseModule build() {
            return new CourseModule(id, title, course, items);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseModule that = (CourseModule) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(course, that.course) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, course, items);
    }

    // toString
    @Override
    public String toString() {
        return "CourseModule{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", course=" + course +
                ", items=" + items +
                '}';
    }
}

