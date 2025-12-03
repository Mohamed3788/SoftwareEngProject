package com.university.lms.StudyBuddy.admin.dto;

import java.util.Objects;

public class AdminDashboardResponse {
    private long totalStudents;
    private long totalTeachers;
    private long totalAdmins;

    private long totalCourses;
    private long totalSubmissions;
    private long totalGradedSubmissions;

    private long totalDiscussionMessages;

    public AdminDashboardResponse() {
    }

    public AdminDashboardResponse(long totalStudents, long totalTeachers, long totalAdmins, long totalCourses, long totalSubmissions, long totalGradedSubmissions, long totalDiscussionMessages) {
        this.totalStudents = totalStudents;
        this.totalTeachers = totalTeachers;
        this.totalAdmins = totalAdmins;
        this.totalCourses = totalCourses;
        this.totalSubmissions = totalSubmissions;
        this.totalGradedSubmissions = totalGradedSubmissions;
        this.totalDiscussionMessages = totalDiscussionMessages;
    }

    // Getters and Setters
    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalTeachers() {
        return totalTeachers;
    }

    public void setTotalTeachers(long totalTeachers) {
        this.totalTeachers = totalTeachers;
    }

    public long getTotalAdmins() {
        return totalAdmins;
    }

    public void setTotalAdmins(long totalAdmins) {
        this.totalAdmins = totalAdmins;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public long getTotalSubmissions() {
        return totalSubmissions;
    }

    public void setTotalSubmissions(long totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }

    public long getTotalGradedSubmissions() {
        return totalGradedSubmissions;
    }

    public void setTotalGradedSubmissions(long totalGradedSubmissions) {
        this.totalGradedSubmissions = totalGradedSubmissions;
    }

    public long getTotalDiscussionMessages() {
        return totalDiscussionMessages;
    }

    public void setTotalDiscussionMessages(long totalDiscussionMessages) {
        this.totalDiscussionMessages = totalDiscussionMessages;
    }

    // Builder pattern
    public static AdminDashboardResponseBuilder builder() {
        return new AdminDashboardResponseBuilder();
    }

    public static class AdminDashboardResponseBuilder {
        private long totalStudents;
        private long totalTeachers;
        private long totalAdmins;
        private long totalCourses;
        private long totalSubmissions;
        private long totalGradedSubmissions;
        private long totalDiscussionMessages;

        AdminDashboardResponseBuilder() {
        }

        public AdminDashboardResponseBuilder totalStudents(long totalStudents) {
            this.totalStudents = totalStudents;
            return this;
        }

        public AdminDashboardResponseBuilder totalTeachers(long totalTeachers) {
            this.totalTeachers = totalTeachers;
            return this;
        }

        public AdminDashboardResponseBuilder totalAdmins(long totalAdmins) {
            this.totalAdmins = totalAdmins;
            return this;
        }

        public AdminDashboardResponseBuilder totalCourses(long totalCourses) {
            this.totalCourses = totalCourses;
            return this;
        }

        public AdminDashboardResponseBuilder totalSubmissions(long totalSubmissions) {
            this.totalSubmissions = totalSubmissions;
            return this;
        }

        public AdminDashboardResponseBuilder totalGradedSubmissions(long totalGradedSubmissions) {
            this.totalGradedSubmissions = totalGradedSubmissions;
            return this;
        }

        public AdminDashboardResponseBuilder totalDiscussionMessages(long totalDiscussionMessages) {
            this.totalDiscussionMessages = totalDiscussionMessages;
            return this;
        }

        public AdminDashboardResponse build() {
            return new AdminDashboardResponse(totalStudents, totalTeachers, totalAdmins, totalCourses, totalSubmissions, totalGradedSubmissions, totalDiscussionMessages);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminDashboardResponse that = (AdminDashboardResponse) o;
        return totalStudents == that.totalStudents && totalTeachers == that.totalTeachers && totalAdmins == that.totalAdmins && totalCourses == that.totalCourses && totalSubmissions == that.totalSubmissions && totalGradedSubmissions == that.totalGradedSubmissions && totalDiscussionMessages == that.totalDiscussionMessages;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalStudents, totalTeachers, totalAdmins, totalCourses, totalSubmissions, totalGradedSubmissions, totalDiscussionMessages);
    }

    // toString
    @Override
    public String toString() {
        return "AdminDashboardResponse{" +
                "totalStudents=" + totalStudents +
                ", totalTeachers=" + totalTeachers +
                ", totalAdmins=" + totalAdmins +
                ", totalCourses=" + totalCourses +
                ", totalSubmissions=" + totalSubmissions +
                ", totalGradedSubmissions=" + totalGradedSubmissions +
                ", totalDiscussionMessages=" + totalDiscussionMessages +
                '}';
    }
}
