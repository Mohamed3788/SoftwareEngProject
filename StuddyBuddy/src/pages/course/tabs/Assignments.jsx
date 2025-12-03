import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useAuth from "../../../hooks/useAuth";
import {
  getStudentAssignments,
  submitAssignment,
  createAssignment,
  getTeacherAssignments,
  getSubmissions,
  gradeSubmission
} from "../../../api/assignments";

export default function Assignments() {
  const { id: courseId } = useParams();
  const auth = useAuth();
  const role = auth?.role;

  const [assignments, setAssignments] = useState([]);
  const [loading, setLoading] = useState(true);

  // STUDENT state
  const [submissionText, setSubmissionText] = useState("");
  const [selectedAssignmentId, setSelectedAssignmentId] = useState(null);

  // TEACHER state
  const [newAssignment, setNewAssignment] = useState({
    title: "",
    description: "",
    dueDate: "",
  });

  const [expandedAssignmentId, setExpandedAssignmentId] = useState(null);
  const [submissions, setSubmissions] = useState([]);
  const [gradingData, setGradingData] = useState({ grade: "", feedback: "" });
  const [gradingSubmissionId, setGradingSubmissionId] = useState(null);

  // ===================== LOAD ======================

  const loadStudentAssignments = async () => {
    const res = await getStudentAssignments();
    const all = res.data || [];
    const filtered = all.filter((a) => a.courseId === Number(courseId));
    setAssignments(filtered);
    setLoading(false);
  };

  const loadTeacherAssignments = async () => {
    const res = await getTeacherAssignments(courseId);
    setAssignments(res.data || []);
    setLoading(false);
  };

  const loadSubmissions = async (assignmentId) => {
    const res = await getSubmissions(assignmentId);
    setSubmissions(res.data || []);
  };

  useEffect(() => {
    if (role === "STUDENT") loadStudentAssignments();
    else loadTeacherAssignments();
  }, [courseId, role]);

  if (loading) return <div>Loading assignments...</div>;

  // ===================== STUDENT SUBMIT ======================

  const handleSubmit = async () => {
    if (!selectedAssignmentId || !submissionText.trim()) return;

    try {
      await submitAssignment(selectedAssignmentId, {
        content: submissionText,
      });

      setSubmissionText("");
      setSelectedAssignmentId(null);
    } catch (err) {
      console.error(err);
    }
  };

  // ===================== TEACHER CREATE ======================

  const handleCreateAssignment = async () => {
    if (!newAssignment.title.trim()) return;

    try {
      await createAssignment({
        ...newAssignment,
        courseId: Number(courseId),
      });

      setNewAssignment({ title: "", description: "", dueDate: "" });
      loadTeacherAssignments();
    } catch (err) {
      console.error(err);
    }
  };

  // ===================== TEACHER GRADE ======================

  const handleGrade = async () => {
    try {
      await gradeSubmission(gradingSubmissionId, {
        grade: gradingData.grade,
        feedback: gradingData.feedback,
      });

      setGradingSubmissionId(null);
      setGradingData({ grade: "", feedback: "" });
      loadSubmissions(expandedAssignmentId);
    } catch (err) {
      console.error(err);
    }
  };

  // ===========================================================
  // ======================= RENDER =============================
  // ===========================================================

  if (role === "STUDENT") {
    return (
      <div>
        <h2 className="text-xl font-bold mb-4">Assignments</h2>

        {assignments.map((a) => (
          <div key={a.id} className="border p-4 rounded mb-4 shadow-sm bg-white">
            <h3 className="text-lg font-semibold">{a.title}</h3>
            {a.dueDate && (
              <p className="text-sm text-gray-600 mb-2">
                Due: {new Date(a.dueDate).toLocaleString()}
              </p>
            )}
            <p className="text-gray-700">{a.description}</p>

            <button
              className="mt-2 text-blue-600 underline"
              onClick={() => setSelectedAssignmentId(a.id)}
            >
              Submit
            </button>
          </div>
        ))}

        {selectedAssignmentId && (
          <div className="mt-6 border-t pt-4">
            <h3 className="font-semibold mb-2">Submit your work</h3>
            <textarea
              className="border p-2 w-full mb-2"
              rows={4}
              value={submissionText}
              onChange={(e) => setSubmissionText(e.target.value)}
            />
            <button
              className="bg-green-600 text-white px-4 py-2 rounded"
              onClick={handleSubmit}
            >
              Submit
            </button>
          </div>
        )}
      </div>
    );
  }

  // ===================== TEACHER VIEW =========================

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6 text-gray-800">Assignments</h2>

      {/* CREATE ASSIGNMENT */}
      <div className="p-6 bg-gradient-to-r from-purple-50 to-pink-50 rounded-lg shadow-md mb-8 border border-purple-200">
        <h3 className="font-bold text-lg mb-4 text-purple-700">Create New Assignment</h3>

        <input
          placeholder="Title"
          className="border p-2 w-full mb-2"
          value={newAssignment.title}
          onChange={(e) =>
            setNewAssignment({ ...newAssignment, title: e.target.value })
          }
        />

        <textarea
          placeholder="Description"
          className="border p-2 w-full mb-2"
          rows={3}
          value={newAssignment.description}
          onChange={(e) =>
            setNewAssignment({ ...newAssignment, description: e.target.value })
          }
        />

        <input
          type="datetime-local"
          className="border p-2 w-full mb-2"
          value={newAssignment.dueDate}
          onChange={(e) =>
            setNewAssignment({ ...newAssignment, dueDate: e.target.value })
          }
        />

        <button
          className="bg-gradient-to-r from-purple-600 to-purple-700 text-white px-6 py-2 rounded-lg font-semibold hover:from-purple-700 hover:to-purple-800 transition-all shadow-md"
          onClick={handleCreateAssignment}
        >
          Create Assignment
        </button>
      </div>

      {/* LIST ASSIGNMENTS */}
      <div className="space-y-4">
        {assignments.length === 0 && (
          <div className="text-center py-8 text-gray-500">
            No assignments created yet. Create your first assignment above!
          </div>
        )}
        {assignments.map((a) => (
          <div key={a.id} className="border border-gray-200 p-5 rounded-lg mb-4 shadow-md bg-white hover:shadow-lg transition-shadow">
          <div
            className="flex justify-between cursor-pointer"
            onClick={() => {
              setExpandedAssignmentId(a.id === expandedAssignmentId ? null : a.id);
              if (a.id !== expandedAssignmentId) loadSubmissions(a.id);
            }}
          >
            <div>
              <h3 className="text-lg font-semibold">{a.title}</h3>
              {a.dueDate && (
                <p className="text-sm text-gray-600">
                  Due: {new Date(a.dueDate).toLocaleString()}
                </p>
              )}
            </div>

            <span className="text-gray-500">
              {expandedAssignmentId === a.id ? "▲" : "▼"}
            </span>
          </div>

          {/* SUBMISSIONS */}
          {expandedAssignmentId === a.id && (
            <div className="mt-4 border-t pt-4">
              <h4 className="font-semibold mb-2">Submissions</h4>

              {submissions.length === 0 && (
                <p className="text-gray-500">No submissions yet.</p>
              )}

              {submissions.map((s) => (
                <div key={s.id} className="border p-3 rounded mb-2 bg-gray-50">
                  <p className="font-medium">{s.studentName}</p>
                  <p className="text-sm text-gray-700 mt-1">{s.content}</p>
                  <p className="text-xs text-gray-500 mt-1">
                    Submitted at: {new Date(s.submittedAt).toLocaleString()}
                  </p>

                  <div className="mt-3">
                    <button
                      className="text-blue-600 underline"
                      onClick={() => {
                        setGradingSubmissionId(s.id);
                        setGradingData({
                          grade: s.grade || "",
                          feedback: s.feedback || "",
                        });
                      }}
                    >
                      Grade
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}
          </div>
        ))}
      </div>

      {/* GRADING PANEL */}
      {gradingSubmissionId && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h3 className="text-lg font-semibold mb-4">Grade Submission</h3>

            <input
              type="number"
              placeholder="Grade"
              className="border p-2 w-full mb-3"
              value={gradingData.grade}
              onChange={(e) =>
                setGradingData({ ...gradingData, grade: e.target.value })
              }
            />

            <textarea
              placeholder="Feedback"
              className="border p-2 w-full mb-3"
              rows={3}
              value={gradingData.feedback}
              onChange={(e) =>
                setGradingData({ ...gradingData, feedback: e.target.value })
              }
            />

            <div className="flex justify-end gap-3">
              <button
                className="px-4 py-2 bg-gray-300 rounded"
                onClick={() => setGradingSubmissionId(null)}
              >
                Cancel
              </button>

              <button
                className="px-4 py-2 bg-green-600 text-white rounded"
                onClick={handleGrade}
              >
                Submit Grade
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}