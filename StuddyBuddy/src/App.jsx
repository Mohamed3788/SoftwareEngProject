import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import AuthProvider from "./context/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";

// Public pages
import Login from "./pages/Login";
import SignupStudent from "./pages/SignupStudent";
import SignupTeacher from "./pages/SignupTeacher";
import SignupAdmin from "./pages/SignupAdmin";

// Layouts
import StudentLayout from "./layouts/StudentLayout";
import TeacherLayout from "./layouts/TeacherLayout";
import AdminLayout from "./layouts/AdminLayout";

// Student pages
import StudentDashboard from "./pages/student/Dashboard";
import StudentCourses from "./pages/student/Courses";
import StudentGradesPage from "./pages/student/StudentGradesPage";
import StudentTodoPage from "./pages/student/StudentTodoPage";

// Teacher pages
import TeacherDashboard from "./pages/teacher/Dashboard";
import TeacherCourses from "./pages/teacher/Courses";
import TeacherCreateAssignmentPage from "./pages/teacher/TeacherCreateAssignmentPage";
import TeacherGradesPage from "./pages/teacher/TeacherGradesPage";
import TeacherCourseGradesPage from "./pages/teacher/TeacherCourseGradesPage";

// Admin pages
import AdminDashboard from "./pages/admin/Dashboard";
import AdminCourses from "./pages/admin/AdminCourses";
import AdminCreateCourse from "./pages/admin/AdminCreateCourse";

// Course pages
import CourseDetailsLayout from "./pages/course/CourseDetailsLayout";
import Modules from "./pages/course/tabs/Modules";
import Assignments from "./pages/course/tabs/Assignments";
import TeacherAssignments from "./pages/course/tabs/TeacherAssignments";
import Discussion from "./pages/course/tabs/Discussion";
import Grades from "./pages/course/tabs/Grades";
import People from "./pages/course/tabs/People";

// Common pages
import CoursePeoplePage from "./pages/common/CoursePeoplePage";
import DiscussionPage from "./pages/common/DiscussionPage";

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          {/* Root redirect */}
          <Route path="/" element={<Navigate to="/login" replace />} />

          {/*  PUBLIC ROUTES  */}
          <Route path="/login" element={<Login />} />
          <Route path="/signup-student" element={<SignupStudent />} />
          <Route path="/signup-teacher" element={<SignupTeacher />} />
          <Route path="/signup-admin" element={<SignupAdmin />} />

          {/* STUDENT ROUTES */}
          <Route
            path="/student"
            element={
              <ProtectedRoute allowedRoles={["STUDENT"]}>
                <StudentLayout />
              </ProtectedRoute>
            }
          >
            <Route index element={<Navigate to="dashboard" replace />} />
            <Route path="dashboard" element={<StudentDashboard />} />
            <Route path="courses" element={<StudentCourses />} />
            <Route path="grades" element={<StudentGradesPage />} />
            <Route path="todo" element={<StudentTodoPage />} />
          </Route>

          {/* Student course routes */}
          <Route path="/student/courses/:id" element={
            <ProtectedRoute allowedRoles={["STUDENT"]}>
              <CourseDetailsLayout />
            </ProtectedRoute>
          }>
            <Route index element={<Navigate to="modules" replace />} />
            <Route path="modules" element={<Modules />} />
            <Route path="assignments" element={<Assignments />} />
            <Route path="discussion" element={<DiscussionPage />} />
            <Route path="people" element={<CoursePeoplePage />} />
            <Route path="grades" element={<Grades />} />
          </Route>

          {/* TEACHER ROUTES*/}
          <Route
            path="/teacher"
            element={
              <ProtectedRoute allowedRoles={["TEACHER"]}>
                <TeacherLayout />
              </ProtectedRoute>
            }
          >
            <Route index element={<Navigate to="dashboard" replace />} />
            <Route path="dashboard" element={<TeacherDashboard />} />
            <Route path="courses" element={<TeacherCourses />} />
            <Route path="grades" element={<TeacherGradesPage />} />
          </Route>

          {/* Teacher course routes */}
          <Route path="/teacher/courses/:id" element={
            <ProtectedRoute allowedRoles={["TEACHER"]}>
              <CourseDetailsLayout />
            </ProtectedRoute>
          }>
            <Route index element={<Navigate to="modules" replace />} />
            <Route path="modules" element={<Modules />} />
            <Route path="assignments" element={<TeacherAssignments />} />
            <Route path="discussion" element={<DiscussionPage />} />
            <Route path="people" element={<CoursePeoplePage />} />
            <Route path="grades" element={<TeacherCourseGradesPage />} />
          </Route>

          {/* Teacher specific routes */}
          <Route
            path="/teacher/courses/:courseId/assignments/create"
            element={
              <ProtectedRoute allowedRoles={["TEACHER"]}>
                <TeacherCreateAssignmentPage />
              </ProtectedRoute>
            }
          />

          {/* ADMIN ROUTES  */}
          <Route
            path="/admin"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <AdminLayout />
              </ProtectedRoute>
            }
          >
            <Route index element={<Navigate to="dashboard" replace />} />
            <Route path="dashboard" element={<AdminDashboard />} />
            <Route path="courses" element={<AdminCourses />} />
            <Route path="courses/new" element={<AdminCreateCourse />} />
          </Route>

          {/* Admin course routes */}
          <Route
            path="/admin/courses/:id/people"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <CoursePeoplePage />
              </ProtectedRoute>
            }
          />

          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
