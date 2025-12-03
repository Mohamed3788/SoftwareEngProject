import axiosClient from "./axiosClient";

// STUDENT — get all grades grouped by course
export const getStudentGrades = () => {
  return axiosClient.get("/student/grades");
};

// TEACHER — get grades for a specific course
export const getTeacherCourseGrades = (courseId) => {
  return axiosClient.get(`/teacher/courses/${courseId}/grades`);
};

// STUDENT: all grades for a course
export function getStudentCourseGrades(courseId) {
  return axiosClient.get(`/student/courses/${courseId}/grades`);
}

export function getStudentAssignments() {
  return axiosClient.get("/student/assignments");
}

export function getTeacherAssignments(courseId) {
  return axiosClient.get(`/teacher/assignments/${courseId}`);
}

export function getTeacherSubmissions(assignmentId) {
  return axiosClient.get(`/teacher/assignments/submissions/${assignmentId}`);
}

export function gradeSubmission(submissionId, data) {
  return axiosClient.post(`/teacher/assignments/grade/${submissionId}`, data);
}