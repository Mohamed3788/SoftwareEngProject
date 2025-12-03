import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import axiosClient from "../../api/axiosClient";

export default function TeacherAssignmentsPage() {
  const { courseId } = useParams();
  const [assignments, setAssignments] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadAssignments = async () => {
    try {
      const res = await axiosClient.get(`/teacher/assignments/${courseId}`);
      setAssignments(res.data);
    } catch (err) {
      console.error("Error loading teacher assignments", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadAssignments();
  }, [courseId]);

  if (loading) return <div className="p-6">Loading assignments...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold mb-6">Assignments</h1>

      <Link
        to={`/teacher/courses/${courseId}/assignments/create`}
        className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
      >
        + Create Assignment
      </Link>

      <div className="mt-6">
        {assignments.map(a => (
          <div
            key={a.id}
            className="border p-4 rounded mb-4 bg-white shadow-sm"
          >
            <div className="text-lg font-semibold">{a.title}</div>
            <div className="text-gray-600">{a.description}</div>
            <div className="text-sm mt-1 text-gray-500">
              Due: {new Date(a.dueDate).toLocaleString()}
            </div>

            <Link
              to={`/teacher/courses/${courseId}/assignments/${a.id}/submissions`}
              className="inline-block mt-3 px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
            >
              View Submissions
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}
