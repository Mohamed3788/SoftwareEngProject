import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  getTeacherAssignments,
  getTeacherSubmissions,
  gradeSubmission,
} from "../../../api/grades";

export default function TeacherGrades() {
  const { id: courseId } = useParams();
  const [assignments, setAssignments] = useState([]);
  const [selected, setSelected] = useState(null);
  const [submissions, setSubmissions] = useState([]);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await getTeacherAssignments(courseId);
        setAssignments(res.data);
      } catch (err) {
        console.error("Failed to load teacher assignments", err);
      }
    };
    load();
  }, [courseId]);

  const loadSubmissions = async (assignmentId) => {
    try {
      const res = await getTeacherSubmissions(assignmentId);
      setSelected(assignmentId);
      setSubmissions(res.data);
    } catch (err) {
      console.error("Failed to load submissions", err);
    }
  };

  const handleGrade = async (submissionId, grade, feedback) => {
    try {
      await gradeSubmission(submissionId, { grade, feedback });
      loadSubmissions(selected);
    } catch (err) {
      console.error("Grade error", err);
    }
  };

  return (
    <div className="p-6">

      <h1 className="text-xl font-bold mb-4">Grades</h1>

      {/* Assignments List */}
      <div className="space-y-3 mb-6">
        {assignments.map(a => (
          <div key={a.id}
            className={`border p-3 rounded cursor-pointer ${
              selected === a.id ? "bg-blue-50" : ""
            }`}
            onClick={() => loadSubmissions(a.id)}
          >
            <div className="font-semibold">{a.title}</div>
            <div className="text-sm text-gray-600">
              Due: {a.dueDate?.replace("T", " at ")}
            </div>
          </div>
        ))}
      </div>

      {/* Submissions */}
      {selected && (
        <div>
          <h2 className="text-lg font-bold mb-3">Submissions</h2>

          {submissions.length === 0 ? (
            <div className="text-gray-600">No submissions yet.</div>
          ) : (
            <div className="space-y-4">
              {submissions.map(s => (
                <SubmissionRow
                  key={s.id}
                  submission={s}
                  onGrade={handleGrade}
                />
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
}

function SubmissionRow({ submission, onGrade }) {
  const [grade, setGrade] = useState(submission.grade || "");
  const [feedback, setFeedback] = useState(submission.feedback || "");

  return (
    <div className="border rounded p-4 bg-white shadow-sm">
      <div className="font-semibold">{submission.studentName}</div>

      <div className="text-sm text-gray-600">
        Submitted: {submission.submittedAt?.replace("T", " at ")}
      </div>

      <textarea
        className="border rounded px-2 py-1 w-full mt-2"
        placeholder="Feedback"
        value={feedback}
        onChange={(e) => setFeedback(e.target.value)}
      />

      <input
        type="number"
        className="border mt-2 px-2 py-1 rounded w-32"
        placeholder="Grade"
        value={grade}
        onChange={(e) => setGrade(e.target.value)}
      />

      <button
        className="mt-3 px-3 py-1 bg-blue-600 text-white rounded"
        onClick={() => onGrade(submission.id, Number(grade), feedback)}
      >
        Save
      </button>
    </div>
  );
}
