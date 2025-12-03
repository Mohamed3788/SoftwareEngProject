import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useAuth from "../../../hooks/useAuth";
import { getStudentCourseGrades } from "../../../api/grades";
import {
  getTeacherAssignments,
  getSubmissions,
  gradeSubmission,
} from "../../../api/assignments";

export default function Grades() {
  const { id: courseId } = useParams();
  const auth = useAuth();
  const role = auth?.role;

  if (role === "STUDENT") {
    return <StudentGrades courseId={courseId} />;
  }
  if (role === "TEACHER") {
    return <TeacherGrades courseId={courseId} />;
  }

  return <div>Grades not available for this role.</div>;
}

// ===================== STUDENT VIEW =====================

function StudentGrades({ courseId }) {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadGrades = async () => {
    try {
      const res = await getStudentCourseGrades(courseId);
      setItems(res.data || []);
    } catch (err) {
      console.error("Failed to load student grades:", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadGrades();
  }, [courseId]);

  if (loading) return <div>Loading grades...</div>;

  return (
    <div>
      <h2 className="text-xl font-bold mb-4">Grades</h2>

      {items.length === 0 ? (
        <p className="text-gray-500">No assignments yet in this course.</p>
      ) : (
        <table className="w-full border-collapse text-sm">
          <thead>
            <tr className="border-b">
              <th className="text-left p-2">Assignment</th>
              <th className="text-left p-2">Due</th>
              <th className="text-left p-2">Status</th>
              <th className="text-left p-2">Submitted</th>
              <th className="text-left p-2">Grade</th>
              <th className="text-left p-2">Feedback</th>
            </tr>
          </thead>
          <tbody>
            {items.map((g) => (
              <tr key={g.assignmentId} className="border-b">
                <td className="p-2">
                  <div className="font-medium">{g.assignmentTitle}</div>
                  {g.assignmentDescription && (
                    <div className="text-xs text-gray-600">
                      {g.assignmentDescription}
                    </div>
                  )}
                </td>
                <td className="p-2 text-xs text-gray-700">
                  {g.dueDate ? new Date(g.dueDate).toLocaleString() : "-"}
                </td>
                <td className="p-2 text-xs">
                  {renderStatusBadge(g.status)}
                </td>
                <td className="p-2 text-xs text-gray-700">
                  {g.submittedAt
                    ? new Date(g.submittedAt).toLocaleString()
                    : "Not submitted"}
                </td>
                <td className="p-2 text-xs font-semibold">
                  {g.grade != null ? g.grade : "-"}
                </td>
                <td className="p-2 text-xs text-gray-700">
                  {g.feedback || "-"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

function renderStatusBadge(status) {
  const base =
    "inline-block px-2 py-1 rounded text-xs font-semibold border";

  if (status === "GRADED") {
    return (
      <span className={`${base} bg-green-100 border-green-400 text-green-700`}>
        GRADED
      </span>
    );
  }
  if (status === "SUBMITTED") {
    return (
      <span className={`${base} bg-yellow-100 border-yellow-400 text-yellow-700`}>
        SUBMITTED
      </span>
    );
  }
  return (
    <span className={`${base} bg-red-100 border-red-400 text-red-700`}>
      NOT SUBMITTED
    </span>
  );
}

// ===================== TEACHER VIEW =====================

function TeacherGrades({ courseId }) {
  const [assignments, setAssignments] = useState([]);
  const [selectedAssignmentId, setSelectedAssignmentId] = useState(null);
  const [submissions, setSubmissions] = useState([]);
  const [loadingAssignments, setLoadingAssignments] = useState(true);
  const [loadingSubs, setLoadingSubs] = useState(false);

  const [gradingSubmissionId, setGradingSubmissionId] = useState(null);
  const [grading, setGrading] = useState({ grade: "", feedback: "" });

  const loadAssignments = async () => {
    try {
      const res = await getTeacherAssignments(courseId);
      setAssignments(res.data || []);
    } catch (err) {
      console.error("Failed to load teacher assignments:", err);
    }
    setLoadingAssignments(false);
  };

  const loadSubmissionsFor = async (assignmentId) => {
    setLoadingSubs(true);
    try {
      const res = await getSubmissions(assignmentId);
      setSubmissions(res.data || []);
    } catch (err) {
      console.error("Failed to load submissions:", err);
    }
    setLoadingSubs(false);
  };

  useEffect(() => {
    loadAssignments();
  }, [courseId]);

  const selectAssignment = (id) => {
    setSelectedAssignmentId(id);
    loadSubmissionsFor(id);
  };

  const openGradeModal = (submission) => {
    setGradingSubmissionId(submission.id);
    setGrading({
      grade: submission.grade ?? "",
      feedback: submission.feedback ?? "",
    });
  };

  const submitGrade = async () => {
    try {
      await gradeSubmission(gradingSubmissionId, {
        grade: grading.grade,
        feedback: grading.feedback,
      });
      setGradingSubmissionId(null);
      setGrading({ grade: "", feedback: "" });
      if (selectedAssignmentId) {
        loadSubmissionsFor(selectedAssignmentId);
      }
    } catch (err) {
      console.error("Grade update failed:", err);
    }
  };

  if (loadingAssignments) return <div>Loading assignments...</div>;

  return (
    <div>
      <h2 className="text-xl font-bold mb-4">Grades (Teacher)</h2>

      {/* Left: assignment list */}
      <div className="grid grid-cols-3 gap-4">
        <div className="col-span-1">
          <h3 className="font-semibold mb-2">Assignments</h3>
          <div className="space-y-2">
            {assignments.map((a) => (
              <div
                key={a.id}
                className={`p-3 border rounded cursor-pointer ${
                  selectedAssignmentId === a.id
                    ? "border-blue-500 bg-blue-50"
                    : "bg-white"
                }`}
                onClick={() => selectAssignment(a.id)}
              >
                <div className="font-medium text-sm">{a.title}</div>
                {a.dueDate && (
                  <div className="text-xs text-gray-600">
                    Due: {new Date(a.dueDate).toLocaleString()}
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>

        {/* Right: submissions table */}
        <div className="col-span-2">
          <h3 className="font-semibold mb-2">Submissions</h3>

          {!selectedAssignmentId ? (
            <p className="text-gray-500 text-sm">
              Select an assignment to see student submissions.
            </p>
          ) : loadingSubs ? (
            <p>Loading submissions...</p>
          ) : submissions.length === 0 ? (
            <p className="text-gray-500 text-sm">No submissions yet.</p>
          ) : (
            <table className="w-full border-collapse text-sm">
              <thead>
                <tr className="border-b">
                  <th className="text-left p-2">Student</th>
                  <th className="text-left p-2">Submitted At</th>
                  <th className="text-left p-2">Content</th>
                  <th className="text-left p-2">Grade</th>
                  <th className="text-left p-2">Feedback</th>
                  <th className="text-left p-2"></th>
                </tr>
              </thead>
              <tbody>
                {submissions.map((s) => (
                  <tr key={s.id} className="border-b align-top">
                    <td className="p-2">{s.studentName}</td>
                    <td className="p-2 text-xs text-gray-700">
                      {s.submittedAt
                        ? new Date(s.submittedAt).toLocaleString()
                        : "-"}
                    </td>
                    <td className="p-2 text-xs text-gray-700 max-w-xs break-words">
                      {s.content}
                    </td>
                    <td className="p-2 text-xs font-semibold">
                      {s.grade != null ? s.grade : "-"}
                    </td>
                    <td className="p-2 text-xs text-gray-700">
                      {s.feedback || "-"}
                    </td>
                    <td className="p-2">
                      <button
                        className="text-blue-600 text-xs underline"
                        onClick={() => openGradeModal(s)}
                      >
                        Grade / Edit
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>

      {/* Grading Modal */}
      {gradingSubmissionId && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h3 className="text-lg font-semibold mb-4">Set Grade</h3>

            <input
              type="number"
              className="border p-2 w-full mb-3"
              placeholder="Grade"
              value={grading.grade}
              onChange={(e) =>
                setGrading({ ...grading, grade: e.target.value })
              }
            />

            <textarea
              className="border p-2 w-full mb-3"
              placeholder="Feedback"
              rows={3}
              value={grading.feedback}
              onChange={(e) =>
                setGrading({ ...grading, feedback: e.target.value })
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
                onClick={submitGrade}
              >
                Save
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
