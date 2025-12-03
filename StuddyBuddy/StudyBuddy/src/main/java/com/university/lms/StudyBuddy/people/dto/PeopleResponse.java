package com.university.lms.StudyBuddy.people.dto;

import java.util.List;
import java.util.Objects;

public class PeopleResponse {

    private TeacherInfo teacher;
    private List<StudentInfo> students;

    public PeopleResponse() {
    }

    public PeopleResponse(TeacherInfo teacher, List<StudentInfo> students) {
        this.teacher = teacher;
        this.students = students;
    }

    // Getters and Setters
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
    public static PeopleResponseBuilder builder() {
        return new PeopleResponseBuilder();
    }

    public static class PeopleResponseBuilder {
        private TeacherInfo teacher;
        private List<StudentInfo> students;

        PeopleResponseBuilder() {
        }

        public PeopleResponseBuilder teacher(TeacherInfo teacher) {
            this.teacher = teacher;
            return this;
        }

        public PeopleResponseBuilder students(List<StudentInfo> students) {
            this.students = students;
            return this;
        }

        public PeopleResponse build() {
            return new PeopleResponse(teacher, students);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeopleResponse that = (PeopleResponse) o;
        return Objects.equals(teacher, that.teacher) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacher, students);
    }

    // toString
    @Override
    public String toString() {
        return "PeopleResponse{" +
                "teacher=" + teacher +
                ", students=" + students +
                '}';
    }

    public static class TeacherInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;

        public TeacherInfo() {
        }

        public TeacherInfo(Long id, String firstName, String lastName, String email) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
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
            private String firstName;
            private String lastName;
            private String email;

            TeacherInfoBuilder() {
            }

            public TeacherInfoBuilder id(Long id) {
                this.id = id;
                return this;
            }

            public TeacherInfoBuilder firstName(String firstName) {
                this.firstName = firstName;
                return this;
            }

            public TeacherInfoBuilder lastName(String lastName) {
                this.lastName = lastName;
                return this;
            }

            public TeacherInfoBuilder email(String email) {
                this.email = email;
                return this;
            }

            public TeacherInfo build() {
                return new TeacherInfo(id, firstName, lastName, email);
            }
        }

        // equals and hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TeacherInfo that = (TeacherInfo) o;
            return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, firstName, lastName, email);
        }

        // toString
        @Override
        public String toString() {
            return "TeacherInfo{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    public static class StudentInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;

        public StudentInfo() {
        }

        public StudentInfo(Long id, String firstName, String lastName, String email) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        // Builder pattern
        public static StudentInfoBuilder builder() {
            return new StudentInfoBuilder();
        }

        public static class StudentInfoBuilder {
            private Long id;
            private String firstName;
            private String lastName;
            private String email;

            StudentInfoBuilder() {
            }

            public StudentInfoBuilder id(Long id) {
                this.id = id;
                return this;
            }

            public StudentInfoBuilder firstName(String firstName) {
                this.firstName = firstName;
                return this;
            }

            public StudentInfoBuilder lastName(String lastName) {
                this.lastName = lastName;
                return this;
            }

            public StudentInfoBuilder email(String email) {
                this.email = email;
                return this;
            }

            public StudentInfo build() {
                return new StudentInfo(id, firstName, lastName, email);
            }
        }

        // equals and hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StudentInfo that = (StudentInfo) o;
            return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, firstName, lastName, email);
        }

        // toString
        @Override
        public String toString() {
            return "StudentInfo{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
