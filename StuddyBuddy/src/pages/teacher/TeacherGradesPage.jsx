import { useEffect, useState } from "react";
import axiosClient from "../../api/axiosClient";
import { useParams } from "react-router-dom";

export default function TeacherGradesPage() {
  const { courseId } = useParams();
  const [grades, setGrades] = useState([]);

  useEffect(() => {
    axiosClient.get(`/teacher/courses/${courseId}/grades`)
      .then(res => setGrades(res.data))
      .catch(err => console.error("Error loading course grades", err));
  }, [courseId]);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold mb-6">Grades Overview</h1>

      {grades.length === 0 && (
        <div>No graded submissions yet.</div>
      )}

      {grades.map(g => (
        <div className="p-4 border rounded mb-3 bg-white shadow-sm" key={g.id}>
          <div className="font-semibold">{g.studentName}</div>
          <div className="text-gray-600">Grade: {g.grade}</div>
          <div className="text-gray-600">Feedback: {g.feedback}</div>
          <div className="text-sm text-gray-500">
            Submitted at: {new Date(g.submittedAt).toLocaleString()}
          </div>
        </div>
      ))}
    </div>
  );
}
