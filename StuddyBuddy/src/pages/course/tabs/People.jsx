import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useAuth from "../../../hooks/useAuth";
import { getStudentPeople, getTeacherPeople } from "../../../api/people";

export default function People() {
  const { id: courseId } = useParams();
  const { role } = useAuth();

  const [teacher, setTeacher] = useState(null);
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadPeople = async () => {
    try {
      const res =
        role === "STUDENT"
          ? await getStudentPeople(courseId)
          : await getTeacherPeople(courseId);

      setTeacher(res.data.teacher);
      setStudents(res.data.students);
    } catch (err) {
      console.error("Failed to load people:", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadPeople();
  }, [courseId, role]);

  if (loading) return <div>Loading people...</div>;

  return (
    <div>
      <h2 className="text-xl font-bold mb-6">People</h2>

      {/* TEACHER INFO */}
      <div className="p-4 mb-6 bg-white shadow rounded border">
        <h3 className="text-lg font-semibold mb-2">Teacher</h3>

        {teacher ? (
          <div className="flex flex-col text-gray-700">
            <span>
              {teacher.firstName} {teacher.lastName}
            </span>
            <span className="text-sm text-blue-600">{teacher.email}</span>
          </div>
        ) : (
          <p className="text-gray-500">No teacher found.</p>
        )}
      </div>

      {/* STUDENT LIST */}
      <div className="p-4 bg-white shadow rounded border">
        <h3 className="text-lg font-semibold mb-4">
          Students ({students.length})
        </h3>

        {students.length === 0 ? (
          <p className="text-gray-500">No students enrolled.</p>
        ) : (
          <div className="space-y-3">
            {students.map((s) => (
              <div
                key={s.id}
                className="p-3 border rounded bg-gray-50 flex justify-between"
              >
                <div>
                  <div className="font-medium">
                    {s.firstName} {s.lastName}
                  </div>
                  <div className="text-sm text-blue-600">{s.email}</div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
