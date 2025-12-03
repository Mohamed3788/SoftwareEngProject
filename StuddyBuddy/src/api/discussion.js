import axiosClient from "./axiosClient";

export function fetchDiscussionHistory(courseId) {
  return axiosClient.get(`/discussion/${courseId}`);
}
