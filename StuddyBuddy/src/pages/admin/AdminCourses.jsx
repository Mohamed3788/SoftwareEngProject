import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getAdminCourses, deleteCourse, updateCourse } from "../../api/admin";
import { getAdminTeachers } from "../../api/admin";

const CLASSES = ["CPI1", "CPI2", "CI1", "CI2", "CI3"];

export default function AdminCourses() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [teachers, setTeachers] = useState([]);
  const [editingCourse, setEditingCourse] = useState(null);
  const [editForm, setEditForm] = useState({
    title: "",
    description: "",
    assignedClass: "",
    teacherId: "",
  });

  const loadCourses = async () => {
    try {
      const res = await getAdminCourses();
      setCourses(res.data || []);
    } catch (err) {
      console.error("Failed to load admin courses:", err);
    }
    setLoading(false);
  };

  const loadTeachers = async () => {
    try {
      const res = await getAdminTeachers();
      setTeachers(res.data || []);
    } catch (err) {
      console.error("Failed to load teachers:", err);
    }
  };

  useEffect(() => {
    loadCourses();
    loadTeachers();
  }, []);

  const handleDelete = async (courseId) => {
    if (!window.confirm("Are you sure you want to delete this course?")) {
      return;
    }

    try {
      await deleteCourse(courseId);
      loadCourses();
    } catch (err) {
      console.error("Failed to delete course:", err);
      alert("Failed to delete course");
    }
  };

  const handleEdit = (course) => {
    setEditingCourse(course.id);
    setEditForm({
      title: course.title,
      description: course.description || "",
      assignedClass: course.assignedClass || "",
      teacherId: course.teacherId || "",
    });
  };

  const handleUpdate = async () => {
    if (!editForm.title.trim() || !editForm.assignedClass || !editForm.teacherId) {
      alert("Please fill in all required fields");
      return;
    }

    try {
      await updateCourse(editingCourse, {
        title: editForm.title,
        description: editForm.description,
        assignedClass: editForm.assignedClass,
        teacherId: Number(editForm.teacherId),
      });
      setEditingCourse(null);
      loadCourses();
    } catch (err) {
      console.error("Failed to update course:", err);
      alert("Failed to update course");
    }
  };

  const handleCancelEdit = () => {
    setEditingCourse(null);
    setEditForm({ title: "", description: "", assignedClass: "", teacherId: "" });
  };

  if (loading) return <div className="p-6">Loading courses...</div>;

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-bold">Courses</h1>
        <Link
          to="/admin/courses/new"
          className="px-4 py-2 bg-blue-600 text-white rounded"
        >
          Create Course
        </Link>
      </div>

      {courses.length === 0 ? (
        <p className="text-gray-500">No courses yet.</p>
      ) : (
        <div className="space-y-4">
          {courses.map((c) => (
            <div
              key={c.id}
              className="border rounded-lg p-4 bg-white shadow-sm"
            >
              {editingCourse === c.id ? (
                <div className="space-y-3">
                  <input
                    type="text"
                    placeholder="Title"
                    className="w-full border p-2 rounded"
                    value={editForm.title}
                    onChange={(e) =>
                      setEditForm({ ...editForm, title: e.target.value })
                    }
                  />
                  <textarea
                    placeholder="Description"
                    className="w-full border p-2 rounded"
                    rows={3}
                    value={editForm.description}
                    onChange={(e) =>
                      setEditForm({ ...editForm, description: e.target.value })
                    }
                  />
                  <select
                    className="w-full border p-2 rounded"
                    value={editForm.assignedClass}
                    onChange={(e) =>
                      setEditForm({ ...editForm, assignedClass: e.target.value })
                    }
                  >
                    <option value="">Select Class</option>
                    {CLASSES.map((cls) => (
                      <option key={cls} value={cls}>
                        {cls}
                      </option>
                    ))}
                  </select>
                  <select
                    className="w-full border p-2 rounded"
                    value={editForm.teacherId}
                    onChange={(e) =>
                      setEditForm({ ...editForm, teacherId: e.target.value })
                    }
                  >
                    <option value="">Select Teacher</option>
                    {teachers.map((t) => (
                      <option key={t.id} value={t.id}>
                        {t.firstName} {t.lastName}
                      </option>
                    ))}
                  </select>
                  <div className="flex gap-2">
                    <button
                      onClick={handleUpdate}
                      className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
                    >
                      Save
                    </button>
                    <button
                      onClick={handleCancelEdit}
                      className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                    >
                      Cancel
                    </button>
                  </div>
                </div>
              ) : (
                <>
                  <div className="flex justify-between items-start mb-2">
                    <div>
                      <h3 className="font-semibold text-lg">{c.title}</h3>
                      <p className="text-sm text-gray-600 mt-1">
                        {c.description || "No description"}
                      </p>
                    </div>
                    <div className="flex gap-2">
                      <button
                        onClick={() => handleEdit(c)}
                        className="px-3 py-1 bg-blue-600 text-white rounded text-sm hover:bg-blue-700"
                      >
                        Edit
                      </button>
                      <button
                        onClick={() => handleDelete(c.id)}
                        className="px-3 py-1 bg-red-600 text-white rounded text-sm hover:bg-red-700"
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                  <div className="text-sm text-gray-600 mt-2">
                    <span className="font-medium">Class:</span> {c.assignedClass || "-"} |{" "}
                    <span className="font-medium">Teacher:</span> {c.teacherName || "-"}
                  </div>
                </>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
