import axiosClient from "./axiosClient";

// Get all todo tasks for the current student
export function getTodoTasks() {
  return axiosClient.get("/student/todo");
}

// Create a new todo task
export function createTodoTask(data) {
  return axiosClient.post("/student/todo", data);
}

// Update a todo task
export function updateTodoTask(id, data) {
  return axiosClient.put(`/student/todo/${id}`, data);
}

// Toggle todo task completion
export function toggleTodoTask(id) {
  return axiosClient.put(`/student/todo/${id}/toggle`);
}

// Delete a todo task
export function deleteTodoTask(id) {
  return axiosClient.delete(`/student/todo/${id}`);
}
