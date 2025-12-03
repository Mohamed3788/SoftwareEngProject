import axiosClient from "./axiosClient";

export function getTeacherAssignments(courseId) {
  return axiosClient.get(`/teacher/assignments/${courseId}`);
}

export function getAssignmentSubmissions(assignmentId) {
  return axiosClient.get(`/teacher/assignments/submissions/${assignmentId}`);
}

export function gradeSubmission(submissionId, payload) {
  return axiosClient.post(`/teacher/assignments/grade/${submissionId}`, payload);
}
