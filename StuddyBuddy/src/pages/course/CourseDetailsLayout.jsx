import { useEffect, useState } from "react";
import { useParams, NavLink, Outlet } from "react-router-dom";
import { getCourseDetails } from "../../api/courseDetails";

export default function CourseDetailsLayout() {
  const { id } = useParams();
  const [course, setCourse] = useState(null);
  const [loading, setLoading] = useState(true);

  const loadCourse = async () => {
    try {
      const res = await getCourseDetails(id);
      setCourse(res.data);
    } catch (err) {
      console.error("Failed to load course", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadCourse();
  }, [id]);

  if (loading) return <div className="p-6">Loading course...</div>;
  if (!course) return <div className="p-6 text-red-600">Course not found.</div>;

  // Detect student/teacher route base
  const tabBase = window.location.pathname.includes("/student")
    ? `/student/courses/${id}`
    : `/teacher/courses/${id}`;

  return (
    <div className="p-6">

      {/* Course Header */}
      <h1 className="text-3xl font-bold mb-2">{course.title}</h1>
      <div className="text-gray-600">{course.description}</div>
      <div className="text-sm text-gray-500 mt-1">
        Teacher: {course.teacherName}
      </div>

      {/* Navigation Tabs */}
      <div className="flex gap-6 mt-6 border-b pb-2">
        <NavLink
          to={`${tabBase}/modules`}
          className={({ isActive }) =>
            isActive ? "font-semibold text-blue-600" : "text-gray-600"
          }
        >
          Modules
        </NavLink>

        <NavLink
          to={`${tabBase}/assignments`}
          className={({ isActive }) =>
            isActive ? "font-semibold text-blue-600" : "text-gray-600"
          }
        >
          Assignments
        </NavLink>

        <NavLink
          to={`${tabBase}/discussion`}
          className={({ isActive }) =>
            isActive ? "font-semibold text-blue-600" : "text-gray-600"
          }
        >
          Discussion
        </NavLink>

        <NavLink
          to={`${tabBase}/people`}
          className={({ isActive }) =>
            isActive ? "font-semibold text-blue-600" : "text-gray-600"
          }
        >
          People
        </NavLink>

        <NavLink
          to={`${tabBase}/grades`}
          className={({ isActive }) =>
            isActive ? "font-semibold text-blue-600" : "text-gray-600"
          }
        >
          Grades
        </NavLink>
      </div>

      {/* Nested Page */}
      <div className="mt-6">
        <Outlet />
      </div>
    </div>
  );
}
