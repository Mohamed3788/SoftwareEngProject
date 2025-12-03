import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getTeacherCourseGrades } from "../../api/grades";

export default function TeacherCourseGradesPage() {
  const { courseId } = useParams();

  const [grades, setGrades] = useState([]);
  const [grouped, setGrouped] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadGrades();
  }, [courseId]);

  const loadGrades = async () => {
    try {
      const res = await getTeacherCourseGrades(courseId);

      setGrades(res.data);

      // GROUP BY STUDENT NAME
      const groupedData = {};
      res.data.forEach((g) => {
        if (!groupedData[g.studentName]) {
          groupedData[g.studentName] = [];
        }
        groupedData[g.studentName].push(g);
      });

      setGrouped(groupedData);
    } catch (err) {
      console.error("Failed to load grades", err);
    }
    setLoading(false);
  };

  if (loading) return <div className="p-6">Loading grades...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Grades Overview</h1>

      {Object.keys(grouped).length === 0 && (
        <div>No submissions yet.</div>
      )}

      {Object.keys(grouped).map((student) => (
        <div key={student} className="mb-10">
          <h2 className="text-lg font-semibold mb-3">{student}</h2>

          <div className="border rounded-lg">
            {grouped[student].map((g) => (
              <div
                key={g.id}
                className="p-4 border-b last:border-b-0 flex justify-between"
              >
                <div>
                  <div className="font-medium">{g.assignmentTitle}</div>
                  <div className="text-gray-600 text-sm">
                    Submitted: {new Date(g.submittedAt).toLocaleString()}
                  </div>
                  {g.feedback && (
                    <div className="text-sm text-blue-700">
                      Feedback: {g.feedback}
                    </div>
                  )}
                </div>

                <div className="text-lg font-bold">
                  {g.grade !== null ? g.grade : "-"}
                </div>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
}
