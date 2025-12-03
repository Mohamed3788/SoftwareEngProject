import axiosClient from "./axiosClient";

// SIGNUP (ADMIN)
export function signupAdmin(data) {
  return axiosClient.post("/auth/signup", data);
}

// SIGNUP (STUDENT)
export function signupStudent(data) {
  return axiosClient.post("/auth/signup-student", data);
}

// SIGNUP (TEACHER)
export function signupTeacher(data) {
  return axiosClient.post("/auth/signup-teacher", data);
}

// LOGIN
export function login(data) {
  return axiosClient.post("/auth/login", data);
}
