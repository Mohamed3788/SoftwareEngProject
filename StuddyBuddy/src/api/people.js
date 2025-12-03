import axiosClient from "./axiosClient";

export function getStudentPeople(courseId) {
  return axiosClient.get(`/student/courses/${courseId}/people`);
}

export function getTeacherPeople(courseId) {
  return axiosClient.get(`/teacher/courses/${courseId}/people`);
}

export function getCoursePeople(courseId) {
  const role = localStorage.getItem("role");

  if (!role) {
    throw new Error("Role not found in localStorage");
  }

  return axiosClient.get(`/${role.toLowerCase()}/courses/${courseId}/people`);
}