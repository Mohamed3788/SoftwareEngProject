import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getCoursePeople } from "../../api/people";

export default function Peoples() {
  const { id } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadData = async () => {
      try {
        const res = await getCoursePeople(id);
        setData(res.data);
      } catch (err) {
        console.error("Failed to load people:", err);
      }
      setLoading(false);
    };

    loadData();
  }, [id]);

  if (loading) return <div className="p-6">Loading...</div>;
  if (!data) return <div className="p-6 text-red-600">Failed to load data.</div>;

  const { teacher, students, courseTitle } = data;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">People – {courseTitle}</h1>

      {/* Teacher */}
      <h2 className="text-xl font-semibold mb-2">Teacher</h2>
      <div className="border p-4 rounded mb-6 bg-gray-50">
        <p className="font-medium">{teacher.fullName}</p>
        <p className="text-sm text-gray-600">{teacher.email}</p>
      </div>

      {/* Students */}
      <h2 className="text-xl font-semibold mb-2">Students</h2>

      {students.length === 0 ? (
        <p className="text-gray-600">No students found for this class.</p>
      ) : (
        <ul className="space-y-3">
          {students.map((s) => (
            <li
              key={s.id}
              className="border p-4 rounded flex justify-between bg-white"
            >
              <div>
                <p className="font-medium">{s.fullName}</p>
                <p className="text-sm text-gray-600">{s.email}</p>
              </div>
              <div className="text-xs text-gray-500">
                {s.studentClass}
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
