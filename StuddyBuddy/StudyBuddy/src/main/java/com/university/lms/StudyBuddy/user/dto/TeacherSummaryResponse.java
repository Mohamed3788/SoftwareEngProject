package com.university.lms.StudyBuddy.user.dto;

import java.util.Objects;

public class TeacherSummaryResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public TeacherSummaryResponse() {
    }

    public TeacherSummaryResponse(Long id, String firstName, String lastName, String email) {
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
    public static TeacherSummaryResponseBuilder builder() {
        return new TeacherSummaryResponseBuilder();
    }

    public static class TeacherSummaryResponseBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;

        TeacherSummaryResponseBuilder() {
        }

        public TeacherSummaryResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TeacherSummaryResponseBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public TeacherSummaryResponseBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public TeacherSummaryResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public TeacherSummaryResponse build() {
            return new TeacherSummaryResponse(id, firstName, lastName, email);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherSummaryResponse that = (TeacherSummaryResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

    // toString
    @Override
    public String toString() {
        return "TeacherSummaryResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
