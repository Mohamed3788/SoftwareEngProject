import { Outlet, Link, useNavigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";

export default function TeacherLayout() {
  const { logout, user } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-indigo-50">
      <nav className="bg-gradient-to-r from-purple-600 to-purple-700 text-white shadow-lg">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center gap-8">
              <Link to="/teacher/dashboard" className="font-semibold text-lg hover:text-purple-200 transition-colors">
                StudyBuddy
              </Link>
              <div className="flex gap-6">
                <Link to="/teacher/dashboard" className="hover:text-purple-200 transition-colors font-medium">
                  Dashboard
                </Link>
                <Link to="/teacher/courses" className="hover:text-purple-200 transition-colors font-medium">
                  Courses
                </Link>
              </div>
            </div>
            <div className="flex items-center gap-4">
              {user && (
                <span className="text-sm text-purple-100">
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
