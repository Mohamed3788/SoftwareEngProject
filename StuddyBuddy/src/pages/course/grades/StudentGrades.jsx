import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getStudentAssignments } from "../../../api/grades";

export default function StudentGrades() {
  const { id: courseId } = useParams();
  const [assignments, setAssignments] = useState([]);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await getStudentAssignments();
        const filtered = res.data.filter(a => a.courseId == courseId);
        setAssignments(filtered);
      } catch (err) {
        console.error("Failed to load student assignments", err);
      }
    };
    load();
  }, [courseId]);

  return (
    <div className="p-6">
      <h1 className="text-xl font-bold mb-4">Grades</h1>

      {assignments.length === 0 ? (
        <div className="text-gray-600">No assignments found.</div>
      ) : (
        <div className="space-y-4">
          {assignments.map(a => (
            <div key={a.id} className="border rounded p-4 bg-white shadow-sm">
              <div className="font-semibold text-lg">{a.title}</div>
              <div className="text-sm text-gray-600">
                Due: {a.dueDate?.replace("T", " at ")}
              </div>

              {/* Submission Status */}
              <StudentAssignmentStatus assignmentId={a.id} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

function StudentAssignmentStatus({ assignmentId }) {
  // This component fetches submission status individually
  const [status, setStatus] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await getStudentAssignments();
        const submission = res.data
          .find(a => a.id === assignmentId)?.submission;

        setStatus(submission || null);
      } catch {}
    };
    load();
  }, [assignmentId]);

  if (!status) return (
    <div className="text-red-600 font-medium mt-2">Not submitted</div>
  );

  return (
    <div className="mt-2">
      <div className="text-green-600 font-medium">
        Submitted at: {status.submittedAt?.replace("T", " at ")}
      </div>

      {status.grade !== null ? (
        <div className="mt-1">
          <div className="font-semibold">Grade: {status.grade}</div>
          <div className="text-sm text-gray-700">
            Feedback: {status.feedback || "No feedback"}
          </div>
        </div>
      ) : (
        <div className="text-yellow-600 font-medium">Awaiting grade…</div>
      )}
    </div>
  );
}
