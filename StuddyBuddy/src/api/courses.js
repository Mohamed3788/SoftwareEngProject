import axiosClient from "./axiosClient";

export function getStudentCourses() {
  return axiosClient.get("/student/courses");
}

export function getTeacherCourses() {
  return axiosClient.get("/teacher/courses");
}