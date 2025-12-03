import { useEffect, useState } from "react";
import { getAdminAdmins } from "../../api/admin";

export default function AdminAdmins() {
  const [admins, setAdmins] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAdminAdmins()
      .then(res => setAdmins(res.data))
      .catch(err => console.error("Failed to load admins", err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="p-6">Loading admins...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Admins</h1>

      {admins.length === 0 ? (
        <p>No admins found.</p>
      ) : (
        <table className="w-full border-collapse text-sm">
          <thead>
            <tr className="border-b">
              <th className="text-left p-2">Name</th>
              <th className="text-left p-2">Email</th>
            </tr>
          </thead>
          <tbody>
            {admins.map(a => (
              <tr key={a.id} className="border-b">
                <td className="p-2">{a.firstName} {a.lastName}</td>
                <td className="p-2 text-gray-600">{a.email}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
