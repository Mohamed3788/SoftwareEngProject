import axiosClient from "./axiosClient";

export function getCourseDetails(id) {
  return axiosClient.get(`/courses/${id}`);
}