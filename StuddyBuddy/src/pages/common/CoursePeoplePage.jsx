import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosClient from "../../api/axiosClient";

export default function CoursePeoplePage() {
  const { id: courseId } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  const loadPeople = async () => {
    try {
      const base = window.location.pathname.includes("/student")
        ? "/student"
        : "/teacher";

      const res = await axiosClient.get(
        `${base}/courses/${courseId}/people`
      );

      setData(res.data);
    } catch (err) {
      console.error("Error loading people:", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadPeople();
  }, [courseId]);

  if (loading) return <div className="p-6">Loading people...</div>;
  if (!data) return <div className="p-6 text-red-600">Unable to load course people.</div>;

  return (
    <div className="p-6">

      <h1 className="text-2xl font-semibold mb-4">People — {data.courseTitle}</h1>

      {/* Teacher Card */}
      <div className="mb-6 p-4 bg-white border rounded shadow-sm">
        <h2 className="text-xl font-semibold mb-2">Teacher</h2>

        <div>
          <div className="font-medium">{data.teacher.fullName}</div>
          <div className="text-gray-600 text-sm">{data.teacher.email}</div>
        </div>
      </div>

      {/* Students List */}
      <div className="p-4 bg-white border rounded shadow-sm">
        <h2 className="text-xl font-semibold mb-3">Students</h2>

        {data.students.length === 0 && (
          <div>No students enrolled.</div>
        )}

        {data.students.map((s) => (
          <div
            key={s.id}
            className="border-b py-2 flex justify-between items-center"
          >
            <div>
              <div className="font-medium">{s.fullName}</div>
              <div className="text-gray-600 text-sm">{s.email}</div>
            </div>

            <div className="text-sm text-gray-500">{s.studentClass}</div>
          </div>
        ))}
      </div>

    </div>
  );
}
