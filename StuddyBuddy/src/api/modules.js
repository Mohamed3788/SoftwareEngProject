import axiosClient from "./axiosClient";

// STUDENT
export function getStudentModules(courseId) {
  return axiosClient.get(`/student/modules/${courseId}`);
}

// TEACHER
export function getTeacherModules(courseId) {
  return axiosClient.get(`/teacher/modules/${courseId}`);
}

export function createModule(data) {
  return axiosClient.post("/teacher/modules", data);
}

export function addModuleItem(moduleId, data) {
  const formData = new FormData();

  // Add type as string (backend will convert to enum)
  const typeValue = data.type.toUpperCase();
  formData.append("type", typeValue);

  // Add content (required even if empty)
  formData.append("content", data.content || "");

  // Add file if provided
  if (data.file) {
    formData.append("file", data.file);
  }

  // Get token to ensure it's available
  const token = localStorage.getItem("token");
  if (!token) {
    return Promise.reject(new Error("No authentication token found"));
  }

  // Don't set Content-Type - axios will set it with boundary automatically
  return axiosClient.post(`/teacher/modules/${moduleId}/items`, formData, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
}
