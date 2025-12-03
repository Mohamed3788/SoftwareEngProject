import { useState } from "react";
import axiosClient from "../../../api/axiosClient";

export default function AssignmentSubmitModal({ assignment, onClose, onSubmitted }) {
  const [text, setText] = useState("");
  const [file, setFile] = useState(null);
  const [sending, setSending] = useState(false);

  const submitText = async () => {
    setSending(true);
    try {
      await axiosClient.post(`/student/assignments/submit/${assignment.id}`, {
        content: text
      });
      onSubmitted();
      onClose();
    } catch (err) {
      console.error(err);
      alert("Submission failed");
    }
    setSending(false);
  };

  const submitFile = async () => {
    if (!file) return alert("Choose a file first");
    setSending(true);

    try {
      const fd = new FormData();
      fd.append("file", file);

      await axiosClient.post(
        `/student/assignments/submit-file/${assignment.id}`,
        fd,
        { headers: { "Content-Type": "multipart/form-data" } }
      );

      onSubmitted();
      onClose();
    } catch (err) {
      console.error(err);
      alert("File upload failed");
    }

    setSending(false);
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex justify-center items-center p-4">
      <div className="bg-white p-6 rounded shadow-lg w-full max-w-lg">

        <h2 className="text-xl font-semibold mb-4">
          Submit Assignment — {assignment.title}
        </h2>

        <div className="mb-4">
          <label className="block font-medium mb-1">Text Submission</label>
          <textarea
            className="w-full border p-2 rounded"
            rows={4}
            value={text}
            onChange={e => setText(e.target.value)}
          />
          <button
            onClick={submitText}
            disabled={sending}
            className="mt-2 px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
          >
            Submit Text
          </button>
        </div>

        <div className="my-4 border-t pt-4">
          <label className="block font-medium mb-1">File Submission</label>
          <input
            type="file"
            className="border p-2 rounded"
            onChange={e => setFile(e.target.files[0])}
          />
          <button
            onClick={submitFile}
            disabled={sending}
            className="mt-2 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
          >
            Upload File
          </button>
        </div>

        <button
          onClick={onClose}
          className="mt-4 text-gray-600 hover:underline"
        >
          Close
        </button>
      </div>
    </div>
  );
}
