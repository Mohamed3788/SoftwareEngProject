import axiosClient from "./axiosClient";

// STUDENT
export function getStudentAssignments() {
  return axiosClient.get("/student/assignments");
}

export function submitAssignment(assignmentId, data) {
  return axiosClient.post(`/student/assignments/submit/${assignmentId}`, data);
}

// TEACHER
export function createAssignment(data) {
  return axiosClient.post("/teacher/assignments", data);
}

export function getTeacherAssignments(courseId) {
  return axiosClient.get(`/teacher/assignments/${courseId}`);
}

export function getSubmissions(assignmentId) {
  return axiosClient.get(`/teacher/assignments/submissions/${assignmentId}`);
}

export function gradeSubmission(submissionId, data) {
  return axiosClient.post(`/teacher/assignments/grade/${submissionId}`, data);
}
