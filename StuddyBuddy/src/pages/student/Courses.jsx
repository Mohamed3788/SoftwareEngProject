import { useEffect, useState } from "react";
import { getStudentCourses } from "../../api/courses";

export default function StudentCourses() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadCourses = async () => {
    try {
      const res = await getStudentCourses();
      setCourses(res.data);
    } catch (err) {
      console.error("Failed to load courses", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadCourses();
  }, []);

  if (loading) {
    return <div className="p-6">Loading courses...</div>;
  }

  return (
    <div className="p-6">

      <h1 className="text-2xl font-bold mb-4">My Courses</h1>

      {courses.length === 0 ? (
        <p className="text-gray-500">No courses assigned to you.</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {courses.map((course) => (
            <div
              key={course.id}
              className="border bg-white p-5 rounded shadow hover:shadow-md transition cursor-pointer"
              onClick={() => (window.location.href = `/student/courses/${course.id}`)}
            >
              <h2 className="text-lg font-semibold">{course.title}</h2>
              <p className="text-gray-600 text-sm mt-1">
                {course.description}
              </p>
              <p className="text-sm text-blue-600 mt-2">
                Class: {course.assignedClass}
              </p>
            </div>
          ))}
        </div>
      )}

    </div>
  );
}
