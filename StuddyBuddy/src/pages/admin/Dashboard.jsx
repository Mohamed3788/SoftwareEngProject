import { useEffect, useState } from "react";
import axiosClient from "../../api/axiosClient";
import useAuth from "../../hooks/useAuth";

export default function Dashboard() {
  const { user } = useAuth();
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axiosClient.get("/admin/dashboard")
      .then(res => setStats(res.data))
      .catch(err => console.error("Failed to load admin dashboard:", err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="p-6">Loading admin dashboard...</div>;
  if (!stats) return <div className="p-6 text-red-600">Unable to load dashboard</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-2">Admin Dashboard</h1>
      {user && (
        <p className="text-gray-600 mb-6">
          Welcome, {user.firstName} {user.lastName}!
        </p>
      )}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatCard title="Students" value={stats.totalStudents} />
        <StatCard title="Teachers" value={stats.totalTeachers} />
        <StatCard title="Admins" value={stats.totalAdmins} />
        <StatCard title="Courses" value={stats.totalCourses} />
      </div>
    </div>
  );
}

function StatCard({ title, value }) {
  return (
    <div className="bg-white shadow rounded border p-6 text-center">
      <h2 className="text-lg font-semibold text-gray-700">{title}</h2>
      <div className="text-3xl font-bold mt-2">{value}</div>
    </div>
  );
}
