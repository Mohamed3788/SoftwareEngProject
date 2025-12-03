import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  getTeacherAssignments,
  getAssignmentSubmissions,
  gradeSubmission
} from "../../../api/teacherAssignments";
import { createAssignment } from "../../../api/assignments";

export default function TeacherAssignments() {
  const { id: courseId } = useParams();
  const [assignments, setAssignments] = useState([]);
  const [selectedAssignment, setSelectedAssignment] = useState(null);
  const [submissions, setSubmissions] = useState([]);
  const [loadingSubs, setLoadingSubs] = useState(false);
  
  // Create assignment state
  const [newAssignment, setNewAssignment] = useState({
    title: "",
    description: "",
    dueDate: "",
  });
  const [showCreateForm, setShowCreateForm] = useState(false);

  useEffect(() => {
    loadAssignments();
  }, [courseId]);

  const loadAssignments = async () => {
    try {
      const res = await getTeacherAssignments(courseId);
      setAssignments(res.data);
    } catch (err) {
      console.error("Failed to load assignments:", err);
    }
  };

  const loadSubmissions = async (assignmentId) => {
    setSelectedAssignment(assignmentId);
    setLoadingSubs(true);

    try {
      const res = await getAssignmentSubmissions(assignmentId);
      setSubmissions(res.data);
    } catch (err) {
      console.error("Error loading submissions:", err);
    }

    setLoadingSubs(false);
  };

  const handleGrade = async (submissionId, grade, feedback) => {
    try {
      await gradeSubmission(submissionId, { grade, feedback });
      loadSubmissions(selectedAssignment); // reload
    } catch (err) {
      console.error("Grade error:", err);
    }
  };

  const handleCreateAssignment = async () => {
    if (!newAssignment.title.trim()) {
      alert("Please enter a title");
      return;
    }
    if (!newAssignment.dueDate) {
      alert("Please select a due date");
      return;
    }

    try {
      await createAssignment({
        ...newAssignment,
        courseId: Number(courseId),
      });
      setNewAssignment({ title: "", description: "", dueDate: "" });
      setShowCreateForm(false);
      loadAssignments();
    } catch (err) {
      console.error("Failed to create assignment:", err);
      alert("Failed to create assignment: " + (err.response?.data?.message || err.message));
    }
  };

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Assignments</h1>
        <button
          onClick={() => setShowCreateForm(!showCreateForm)}
          className="bg-gradient-to-r from-purple-600 to-purple-700 text-white px-6 py-2 rounded-lg font-semibold hover:from-purple-700 hover:to-purple-800 transition-all shadow-md"
        >
          {showCreateForm ? "Cancel" : "+ Create Assignment"}
        </button>
      </div>

      {/* CREATE ASSIGNMENT FORM */}
      {showCreateForm && (
        <div className="p-6 bg-gradient-to-r from-purple-50 to-pink-50 rounded-lg shadow-md mb-8 border border-purple-200">
          <h3 className="font-bold text-lg mb-4 text-purple-700">Create New Assignment</h3>

          <input
            placeholder="Title *"
            className="border p-2 w-full mb-2 rounded"
            value={newAssignment.title}
            onChange={(e) =>
              setNewAssignment({ ...newAssignment, title: e.target.value })
            }
          />

          <textarea
            placeholder="Description"
            className="border p-2 w-full mb-2 rounded"
            rows={3}
            value={newAssignment.description}
            onChange={(e) =>
              setNewAssignment({ ...newAssignment, description: e.target.value })
            }
          />

          <input
            type="datetime-local"
            className="border p-2 w-full mb-2 rounded"
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
      )}

      {/* Assignment list */}
      <div className="space-y-3 mb-6">
        {assignments.map(a => (
          <div
            key={a.id}
            className={`border p-3 rounded cursor-pointer ${
              selectedAssignment === a.id ? "bg-blue-50" : ""
            }`}
            onClick={() => loadSubmissions(a.id)}
          >
            <div className="font-semibold text-lg">{a.title}</div>
            <div className="text-sm text-gray-600">
              Due: {a.dueDate?.replace("T", " at ")}
            </div>
          </div>
        ))}
      </div>

      {/* Submissions Section */}
      {selectedAssignment && (
        <div>
          <h2 className="text-xl font-semibold mb-3">Submissions</h2>

          {loadingSubs ? (
            <div>Loading submissions...</div>
          ) : submissions.length === 0 ? (
            <div className="text-gray-600">No submissions yet.</div>
          ) : (
            submissions.map(s => (
              <SubmissionRow
                key={s.id}
                submission={s}
                onGrade={handleGrade}
              />
            ))
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
    <div className="border rounded p-4 mb-4 bg-white">
      <div className="font-medium">{submission.studentName}</div>

      <div className="text-sm text-gray-600">
        Submitted: {submission.submittedAt?.replace("T", " at ")}
      </div>

      {/* Submission Content */}
      <div className="mt-2 p-2 bg-gray-50 rounded border">
        {submission.content || "No content"}
      </div>

      {/* Grade input */}
      <input
        type="number"
        className="border rounded px-2 py-1 mt-2 w-32"
        placeholder="Grade"
        value={grade}
        onChange={(e) => setGrade(e.target.value)}
      />

      {/* Feedback input */}
      <textarea
        className="border rounded px-2 py-1 w-full mt-2"
        rows={2}
        placeholder="Feedback"
        value={feedback}
        onChange={(e) => setFeedback(e.target.value)}
      ></textarea>

      {/* Save button */}
      <button
        className="mt-3 px-3 py-1 bg-blue-600 text-white rounded"
        onClick={() => onGrade(submission.id, Number(grade), feedback)}
      >
        Save
      </button>
    </div>
  );
}
