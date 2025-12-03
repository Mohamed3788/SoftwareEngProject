import { useEffect, useState } from "react";
import axiosClient from "../../api/axiosClient";

export default function StudentGradesPage() {
  const [grouped, setGrouped] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axiosClient
      .get("/student/grades")
      .then((res) => {
        const grades = res.data;

        // GROUP BY courseTitle
        const map = {};
        grades.forEach((g) => {
          if (!map[g.courseTitle]) {
            map[g.courseTitle] = [];
          }
          map[g.courseTitle].push(g);
        });

        setGrouped(map);
      })
      .catch((err) => console.error("Error loading grades", err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="p-6">Loading grades...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold mb-6">My Grades</h1>

      {Object.keys(grouped).length === 0 && (
        <div>No graded assignments yet.</div>
      )}

      {Object.keys(grouped).map((courseTitle) => (
        <div key={courseTitle} className="mb-10">
          <h2 className="text-xl font-semibold mb-4">{courseTitle}</h2>

          {grouped[courseTitle].map((g) => (
            <div
              className="p-4 border rounded mb-3 bg-white shadow-sm"
              key={g.id}
            >
              <div className="font-semibold">{g.assignmentTitle}</div>

              <div className="text-gray-600">
                Grade: <span className="font-bold">{g.grade ?? "-"}</span>
              </div>

              {g.feedback && (
                <div className="text-gray-600">
                  Feedback: <span className="italic">{g.feedback}</span>
                </div>
              )}

              <div className="text-sm text-gray-500">
                Submitted: {new Date(g.submittedAt).toLocaleString()}
              </div>
            </div>
          ))}
        </div>
      ))}
    </div>
  );
}
