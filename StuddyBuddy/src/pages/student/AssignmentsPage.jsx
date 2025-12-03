import { useEffect, useState } from "react";
import axiosClient from "../../../api/axiosClient";
import AssignmentSubmitModal from "./AssignmentSubmitModal";

export default function AssignmentsPage() {
  const [assignments, setAssignments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedAssignment, setSelectedAssignment] = useState(null);

  const loadAssignments = async () => {
    try {
      const res = await axiosClient.get("/student/assignments");
      setAssignments(res.data);
    } catch (err) {
      console.error("Failed to load assignments", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadAssignments();
  }, []);

  if (loading) return <div className="p-6">Loading...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold mb-4">Assignments</h1>

      {assignments.map(a => (
        <div
          key={a.id}
          className="border p-4 rounded mb-4 bg-white shadow-sm"
        >
          <div className="font-semibold text-lg">{a.title}</div>
          <div className="text-gray-600">{a.description}</div>

          <div className="mt-2 text-sm">
            <span className="font-medium">Course:</span> {a.courseTitle}
          </div>

          <div className="text-sm text-gray-500">
            Due: {new Date(a.dueDate).toLocaleString()}
          </div>

          <button
            onClick={() => setSelectedAssignment(a)}
            className="mt-3 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
          >
            Submit
          </button>
        </div>
      ))}

      {selectedAssignment && (
        <AssignmentSubmitModal
          assignment={selectedAssignment}
          onClose={() => setSelectedAssignment(null)}
          onSubmitted={loadAssignments}
        />
      )}
    </div>
  );
}
