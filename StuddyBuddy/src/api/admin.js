import axiosClient from "./axiosClient";

export function getAdminCourses() {
  return axiosClient.get("/admin/courses");
}

export function getAdminTeachers() {
  return axiosClient.get("/admin/teachers");
}

export function createCourse(data) {
  // data doit matcher CourseCreateRequest côté backend
  return axiosClient.post("/admin/courses", data);
}

export function getAdminStudents() {
  return axiosClient.get("/admin/users/students");
}

export function getAdminAdmins() {
  return axiosClient.get("/admin/users/admins");
}

export function deleteCourse(courseId) {
  return axiosClient.delete(`/admin/courses/${courseId}`);
}

export function updateCourse(courseId, data) {
  return axiosClient.put(`/admin/courses/${courseId}`, data);
}