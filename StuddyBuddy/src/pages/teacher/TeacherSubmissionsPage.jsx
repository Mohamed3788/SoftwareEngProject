import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosClient from "../../api/axiosClient";
import GradeSubmissionModal from "./GradeSubmissionModal";

export default function TeacherSubmissionsPage() {
  const { courseId, assignmentId } = useParams();
  const [submissions, setSubmissions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedSubmission, setSelectedSubmission] = useState(null);

  const loadSubmissions = async () => {
    try {
      const res = await axiosClient.get(
        `/teacher/assignments/submissions/${assignmentId}`
      );
      setSubmissions(res.data);
    } catch (err) {
      console.error("Error loading submissions", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadSubmissions();
  }, [assignmentId]);

  if (loading) return <div className="p-6">Loading submissions...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold mb-4">Submissions</h1>

      {submissions.map((s) => (
        <div
          key={s.id}
          className="p-4 border rounded mb-3 bg-white shadow-sm"
        >
          <div className="font-semibold">{s.studentName}</div>

          {/* Show file or text */}
          {s.content && s.content.startsWith("/files/") ? (
            <a
              href={`http://localhost:8080${s.content}`}
              target="_blank"
              rel="noreferrer"
              className="text-blue-600 underline block mt-1"
            >
              Open Submitted File
            </a>
          ) : (
            <div className="mt-1 text-gray-700 whitespace-pre-wrap">
              {s.content}
            </div>
          )}

          <div className="text-sm text-gray-500 mt-1">
            Submitted at: {new Date(s.submittedAt).toLocaleString()}
          </div>

          {s.grade !== null && (
            <div className="text-sm text-green-700 mt-1">
              <strong>Grade:</strong> {s.grade}
            </div>
          )}

          {s.feedback && (
            <div className="text-sm text-gray-700">
              <strong>Feedback:</strong> {s.feedback}
            </div>
          )}

          <button
            onClick={() => setSelectedSubmission(s)}
            className="mt-3 px-4 py-2 bg-purple-600 text-white rounded hover:bg-purple-700"
          >
            {s.grade === null ? "Grade" : "Edit Grade"}
          </button>
        </div>
      ))}

      {selectedSubmission && (
        <GradeSubmissionModal
          submission={selectedSubmission}
          onClose={() => setSelectedSubmission(null)}
          onGraded={loadSubmissions}
        />
      )}
    </div>
  );
}
