import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { createCourse, getAdminTeachers } from "../../api/admin";

const CLASSES = ["CPI1", "CPI2", "CI1", "CI2", "CI3"];

export default function AdminCreateCourse() {
  const navigate = useNavigate();

  const [teachers, setTeachers] = useState([]);
  const [loadingTeachers, setLoadingTeachers] = useState(true);

  const [form, setForm] = useState({
    title: "",
    description: "",
    assignedClass: "",
    teacherId: "",
  });

  const [submitting, setSubmitting] = useState(false);

  const loadTeachers = async () => {
    try {
      const res = await getAdminTeachers();
      setTeachers(res.data || []);
    } catch (err) {
      console.error("Failed to load teachers:", err);
    }
    setLoadingTeachers(false);
  };

  useEffect(() => {
    loadTeachers();
  }, []);

  const handleChange = (field) => (e) => {
    setForm({ ...form, [field]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.title || !form.assignedClass || !form.teacherId) return;

    setSubmitting(true);
    try {
      await createCourse({
        title: form.title,
        description: form.description,
        assignedClass: form.assignedClass,
        teacherId: Number(form.teacherId),
      });

      navigate("/admin/courses");
    } catch (err) {
      console.error("Failed to create course:", err);
    }
    setSubmitting(false);
  };

  return (
    <div className="p-6 max-w-xl">
      <h1 className="text-2xl font-bold mb-4">Create Course</h1>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">Title</label>
          <input
            type="text"
            className="border rounded px-3 py-2 w-full"
            value={form.title}
            onChange={handleChange("title")}
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Description</label>
          <textarea
            className="border rounded px-3 py-2 w-full"
            rows={3}
            value={form.description}
            onChange={handleChange("description")}
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Class</label>
          <select
            className="border rounded px-3 py-2 w-full"
            value={form.assignedClass}
            onChange={handleChange("assignedClass")}
            required
          >
            <option value="">Select a class</option>
            {CLASSES.map((c) => (
              <option key={c} value={c}>
                {c}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Teacher</label>
          {loadingTeachers ? (
            <div className="text-gray-500 text-sm">Loading teachers...</div>
          ) : teachers.length === 0 ? (
            <div className="text-red-600 text-sm">
              No teachers available. Create teacher accounts first.
            </div>
          ) : (
            <select
              className="border rounded px-3 py-2 w-full"
              value={form.teacherId}
              onChange={handleChange("teacherId")}
              required
            >
              <option value="">Select a teacher</option>
              {teachers.map((t) => (
                <option key={t.id} value={t.id}>
                  {t.firstName} {t.lastName} ({t.email})
                </option>
              ))}
            </select>
          )}
        </div>

        <div className="flex justify-end gap-3 mt-4">
          <button
            type="button"
            className="px-4 py-2 bg-gray-300 rounded"
            onClick={() => navigate("/admin/courses")}
          >
            Cancel
          </button>
          <button
            type="submit"
            disabled={submitting}
            className="px-4 py-2 bg-blue-600 text-white rounded disabled:opacity-60"
          >
            {submitting ? "Creating..." : "Create"}
          </button>
        </div>
      </form>
    </div>
  );
}
