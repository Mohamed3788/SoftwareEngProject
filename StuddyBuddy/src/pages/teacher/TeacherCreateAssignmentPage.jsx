import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axiosClient from "../../api/axiosClient";

export default function TeacherCreateAssignmentPage() {
  const { courseId } = useParams();
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [dueDate, setDueDate] = useState("");

  const [sending, setSending] = useState(false);

  const submitAssignment = async () => {
    if (!title.trim()) return alert("Title is required");
    if (!dueDate) return alert("Due date is required");

    setSending(true);

    try {
      await axiosClient.post("/teacher/assignments", {
        title,
        description,
        dueDate,
        courseId: Number(courseId)
      });

      // Return to assignments list
      navigate(`/teacher/courses/${courseId}/assignments`);
    } catch (err) {
      console.error(err);
      alert("Failed to create assignment");
    }

    setSending(false);
  };

  return (
    <div className="p-6 max-w-xl mx-auto">

      <h1 className="text-2xl font-semibold mb-6">
        Create Assignment
      </h1>

      <div className="mb-4">
        <label className="block font-medium mb-1">Title</label>
        <input
          className="w-full border p-2 rounded"
          type="text"
          value={title}
          onChange={e => setTitle(e.target.value)}
          placeholder="Assignment title..."
        />
      </div>

      <div className="mb-4">
        <label className="block font-medium mb-1">Description</label>
        <textarea
          className="w-full border p-2 rounded"
          rows={4}
          value={description}
          onChange={e => setDescription(e.target.value)}
          placeholder="Assignment description..."
        ></textarea>
      </div>

      <div className="mb-4">
        <label className="block font-medium mb-1">Due Date</label>
        <input
          type="datetime-local"
          className="w-full border p-2 rounded"
          value={dueDate}
          onChange={e => setDueDate(e.target.value)}
        />
      </div>

      <button
        onClick={submitAssignment}
        disabled={sending}
        className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
      >
        Create Assignment
      </button>

      <button
        onClick={() => navigate(-1)}
        className="ml-3 text-gray-600 hover:underline"
      >
        Cancel
      </button>
    </div>
  );
}
