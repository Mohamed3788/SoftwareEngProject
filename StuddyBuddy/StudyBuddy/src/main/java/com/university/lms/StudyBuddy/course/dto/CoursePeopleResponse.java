package com.university.lms.StudyBuddy.course.dto;

import com.university.lms.StudyBuddy.user.model.StudentClass;

import java.util.List;
import java.util.Objects;

public class CoursePeopleResponse {

    private Long courseId;
    private String courseTitle;

    private TeacherInfo teacher;
    private List<StudentInfo> students;

    public CoursePeopleResponse() {
    }

    public CoursePeopleResponse(Long courseId, String courseTitle, TeacherInfo teacher, List<StudentInfo> students) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.teacher = teacher;
        this.students = students;
    }

    // Getters and Setters
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public TeacherInfo getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherInfo teacher) {
        this.teacher = teacher;
    }

    public List<StudentInfo> getStudents() {
        return students;
    }

    public void setStudents(List<StudentInfo> students) {
        this.students = students;
    }

    // Builder pattern
    public static CoursePeopleResponseBuilder builder() {
        return new CoursePeopleResponseBuilder();
    }

    public static class CoursePeopleResponseBuilder {
        private Long courseId;
        private String courseTitle;
        private TeacherInfo teacher;
        private List<StudentInfo> students;

        CoursePeopleResponseBuilder() {
        }

        public CoursePeopleResponseBuilder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public CoursePeopleResponseBuilder courseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
            return this;
        }

        public CoursePeopleResponseBuilder teacher(TeacherInfo teacher) {
            this.teacher = teacher;
            return this;
        }

        public CoursePeopleResponseBuilder students(List<StudentInfo> students) {
            this.students = students;
            return this;
        }

        public CoursePeopleResponse build() {
            return new CoursePeopleResponse(courseId, courseTitle, teacher, students);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoursePeopleResponse that = (CoursePeopleResponse) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(courseTitle, that.courseTitle) && Objects.equals(teacher, that.teacher) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseTitle, teacher, students);
    }

    // toString
    @Override
    public String toString() {
        return "CoursePeopleResponse{" +
                "courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }

    public static class TeacherInfo {
        private Long id;
        private String fullName;
        private String email;

        public TeacherInfo() {
        }

        public TeacherInfo(Long id, String fullName, String email) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        // Builder pattern
        public static TeacherInfoBuilder builder() {
            return new TeacherInfoBuilder();
        }

        public static class TeacherInfoBuilder {
            private Long id;
            private String fullName;
            private String email;

            TeacherInfoBuilder() {
            }

            public TeacherInfoBuilder id(Long id) {
                this.id = id;
                return this;
            }

            public TeacherInfoBuilder fullName(String fullName) {
                this.fullName = fullName;
                return this;
            }

            public TeacherInfoBuilder email(String email) {
                this.email = email;
                return this;
            }

            public TeacherInfo build() {
                return new TeacherInfo(id, fullName, email);
            }
        }

        // equals and hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TeacherInfo that = (TeacherInfo) o;
            return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName) && Objects.equals(email, that.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, fullName, email);
        }

        // toString
        @Override
        public String toString() {
            return "TeacherInfo{" +
                    "id=" + id +
                    ", fullName='" + fullName + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    public static class StudentInfo {
        private Long id;
        private String fullName;
        private String email;
        private StudentClass studentClass;

        public StudentInfo() {
        }

        public StudentInfo(Long id, String fullName, String email, StudentClass studentClass) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.studentClass = studentClass;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public StudentClass getStudentClass() {
            return studentClass;
        }

        public void setStudentClass(StudentClass studentClass) {
            this.studentClass = studentClass;
        }

        // Builder pattern
        public static StudentInfoBuilder builder() {
            return new StudentInfoBuilder();
        }

        public static class StudentInfoBuilder {
            private Long id;
            private String fullName;
            private String email;
            private StudentClass studentClass;

            StudentInfoBuilder() {
            }

            public StudentInfoBuilder id(Long id) {
                this.id = id;
                return this;
            }

            public StudentInfoBuilder fullName(String fullName) {
                this.fullName = fullName;
                return this;
            }

            public StudentInfoBuilder email(String email) {
                this.email = email;
                return this;
            }

            public StudentInfoBuilder studentClass(StudentClass studentClass) {
                this.studentClass = studentClass;
                return this;
            }

            public StudentInfo build() {
                return new StudentInfo(id, fullName, email, studentClass);
            }
        }

        // equals and hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StudentInfo that = (StudentInfo) o;
            return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName) && Objects.equals(email, that.email) && studentClass == that.studentClass;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, fullName, email, studentClass);
        }

        // toString
        @Override
        public String toString() {
            return "StudentInfo{" +
                    "id=" + id +
                    ", fullName='" + fullName + '\'' +
                    ", email='" + email + '\'' +
                    ", studentClass=" + studentClass +
                    '}';
        }
    }
}
