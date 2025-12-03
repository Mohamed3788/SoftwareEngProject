import { useEffect, useState } from "react";
import axiosClient from "../../api/axiosClient";
import useAuth from "../../hooks/useAuth";

export default function TeacherDashboard() {
  const auth = useAuth();
  const user = auth?.user;
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadEvents = async () => {
    try {
      const res = await axiosClient.get("/dashboard");
      setEvents(res.data);
    } catch (err) {
      console.error("Failed to load teacher dashboard events", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadEvents();
  }, []);

  if (loading) {
    return <div className="p-6 text-gray-600">Loading dashboard...</div>;
  }

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-2">Teacher Dashboard</h1>
      {user && (
        <p className="text-gray-600 mb-2">
          Welcome, {user.firstName} {user.lastName}!
        </p>
      )}
      <p className="text-gray-600 mb-6">Recent activity in your courses</p>

      {events.length === 0 ? (
        <div className="text-gray-500">No recent activity.</div>
      ) : (
        <div className="space-y-3">
          {events.map((event) => (
            <div
              key={event.id}
              className="bg-white border rounded p-4 shadow-sm"
            >
              <div className="font-semibold">{event.message}</div>

              <div className="text-sm text-gray-500">
                {new Date(event.timestamp).toLocaleString()}
              </div>

              <div className="text-xs text-blue-600 mt-1">
                {event.eventType}
              </div>
            </div>
          ))}
        </div>
      )}

    </div>
  );
}
