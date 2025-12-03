package com.university.lms.StudyBuddy.auth.dto;


import com.university.lms.StudyBuddy.user.model.Role;
import com.university.lms.StudyBuddy.user.model.StudentClass;

import java.util.Objects;

public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private StudentClass studentClass;

    public UserResponse() {
    }

    public UserResponse(Long id, String firstName, String lastName, String email, Role role, StudentClass studentClass) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.studentClass = studentClass;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
    }

    // Builder pattern
    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }

    public static class UserResponseBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;
        private StudentClass studentClass;

        UserResponseBuilder() {
        }

        public UserResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserResponseBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserResponseBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserResponseBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserResponseBuilder studentClass(StudentClass studentClass) {
            this.studentClass = studentClass;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(id, firstName, lastName, email, role, studentClass);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && role == that.role && studentClass == that.studentClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, role, studentClass);
    }

    // toString
    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", studentClass=" + studentClass +
                '}';
    }
}
