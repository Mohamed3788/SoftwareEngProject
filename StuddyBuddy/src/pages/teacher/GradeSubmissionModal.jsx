import { useState } from "react";
import axiosClient from "../../api/axiosClient";

export default function GradeSubmissionModal({ submission, onClose, onGraded }) {
  const [grade, setGrade] = useState(submission.grade || "");
  const [feedback, setFeedback] = useState(submission.feedback || "");
  const [sending, setSending] = useState(false);

  const submitGrade = async () => {
    if (grade === "" || isNaN(grade)) {
      return alert("Grade must be a valid number");
    }

    setSending(true);

    try {
      await axiosClient.post(
        `/teacher/assignments/grade/${submission.id}`,
        { grade, feedback }
      );

      onGraded(); // refresh list
      onClose();  // close modal
    } catch (err) {
      console.error(err);
      alert("Failed to submit grade");
    }

    setSending(false);
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex justify-center items-center p-4">
      <div className="bg-white p-6 rounded shadow-lg w-full max-w-lg">
        
        <h2 className="text-xl font-semibold mb-4">
          Grade Submission – {submission.studentName}
        </h2>

        <div className="mb-4">
          <label className="block font-medium mb-1">Grade</label>
          <input
            type="number"
            value={grade}
            onChange={(e) => setGrade(e.target.value)}
            className="w-full border p-2 rounded"
            placeholder="Enter numeric grade"
          />
        </div>

        <div className="mb-4">
          <label className="block font-medium mb-1">Feedback</label>
          <textarea
            value={feedback}
            onChange={(e) => setFeedback(e.target.value)}
            className="w-full border p-2 rounded"
            rows={4}
            placeholder="Write feedback..."
          ></textarea>
        </div>

        <button
          onClick={submitGrade}
          disabled={sending}
          className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
        >
          Submit Grade
        </button>

        <button
          onClick={onClose}
          className="ml-3 text-gray-600 hover:underline"
        >
          Cancel
        </button>
      </div>
    </div>
  );
}
