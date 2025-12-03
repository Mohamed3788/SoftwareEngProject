import { Outlet, Link, useNavigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";

export default function StudentLayout() {
  const { logout, user } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50">
      <nav className="bg-gradient-to-r from-blue-600 to-blue-700 text-white shadow-lg">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center gap-8">
              <Link to="/student/dashboard" className="font-semibold text-lg hover:text-blue-200 transition-colors">
                StudyBuddy
              </Link>
              <div className="flex gap-6">
                <Link to="/student/dashboard" className="hover:text-blue-200 transition-colors font-medium">
                  Dashboard
                </Link>
                <Link to="/student/courses" className="hover:text-blue-200 transition-colors font-medium">
                  Courses
                </Link>
                <Link to="/student/todo" className="hover:text-blue-200 transition-colors font-medium">
                  To-Do List
                </Link>
              </div>
            </div>
            <div className="flex items-center gap-4">
              {user && (
                <span className="text-sm text-blue-100">
                  {user.firstName} {user.lastName}
                </span>
              )}
              <button
                onClick={handleLogout}
                className="bg-red-500 hover:bg-red-600 px-4 py-2 rounded-lg transition-colors font-medium shadow-md"
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      </nav>
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <Outlet />
      </div>
    </div>
  );
}
