import { useEffect, useState } from "react";
import { getAdminStudents } from "../../api/admin";

export default function AdminStudents() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAdminStudents()
      .then(res => setStudents(res.data))
      .catch(err => console.error("Failed to load students", err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="p-6">Loading students...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Students</h1>

      {students.length === 0 ? (
        <p>No students found.</p>
      ) : (
        <table className="w-full border-collapse text-sm">
          <thead>
            <tr className="border-b">
              <th className="text-left p-2">Name</th>
              <th className="text-left p-2">Email</th>
            </tr>
          </thead>
          <tbody>
            {students.map(s => (
              <tr key={s.id} className="border-b">
                <td className="p-2">{s.firstName} {s.lastName}</td>
                <td className="p-2 text-gray-600">{s.email}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
