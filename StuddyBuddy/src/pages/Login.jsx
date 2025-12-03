import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import useAuth from "../hooks/useAuth";

export default function Login() {
  const auth = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  if (!auth) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-gray-600">Loading...</div>
      </div>
    );
  }

  const { login } = auth;

  const doLogin = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await login(email, password);
      
      // Get user from localStorage to determine role
      const userStr = localStorage.getItem("user");
      if (userStr) {
        const user = JSON.parse(userStr);
        // Redirect based on role
        switch (user.role) {
          case "STUDENT":
            navigate("/student/dashboard");
            break;
          case "TEACHER":
            navigate("/teacher/dashboard");
            break;
          case "ADMIN":
            navigate("/admin/dashboard");
            break;
          default:
            navigate("/");
        }
      }
    } catch (err) {
      console.error("Login error:", err);
      
      // Try to extract error message from various possible locations
      let errorMessage = "Login failed. Please check your email and password.";
      
      if (err.response) {
        // Check different possible error message formats
        errorMessage = 
          err.response.data?.message || 
          err.response.data?.error || 
          (typeof err.response.data === 'string' ? err.response.data : null) ||
          errorMessage;
      } else if (err.message) {
        errorMessage = err.message;
      }
      
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        <div className="bg-white rounded-2xl shadow-xl p-8">
          {/* Header */}
          <div className="text-center mb-8">
            <h1 className="text-4xl font-bold text-gray-900 mb-2">StudyBuddy</h1>
            <p className="text-gray-600">Learning Management System</p>
          </div>

          <h2 className="text-2xl font-semibold text-gray-800 mb-6">Sign in to your account</h2>

          {/* Error Message */}
          {error && (
            <div className="mb-4 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
              {error}
            </div>
          )}

          {/* Login Form */}
          <form onSubmit={doLogin} className="space-y-6">
            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-2">
                Email Address
              </label>
              <input
                id="email"
                type="email"
                required
                className="input-field"
                placeholder="you@example.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                disabled={loading}
              />
            </div>

            <div>
              <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-2">
                Password
              </label>
              <input
                id="password"
                type="password"
                required
                className="input-field"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                disabled={loading}
              />
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full btn-primary disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {loading ? "Signing in..." : "Sign In"}
            </button>
          </form>

          {/* Signup Links */}
          <div className="mt-6 pt-6 border-t border-gray-200">
            <p className="text-center text-sm text-gray-600 mb-4">
              Don't have an account? Sign up as:
            </p>
            <div className="flex flex-col space-y-2">
              <Link
                to="/signup-student"
                className="text-center text-blue-600 hover:text-blue-800 font-medium transition-colors"
              >
                Student
              </Link>
              <Link
                to="/signup-teacher"
                className="text-center text-blue-600 hover:text-blue-800 font-medium transition-colors"
              >
                Teacher
              </Link>
              <Link
                to="/signup-admin"
                className="text-center text-blue-600 hover:text-blue-800 font-medium transition-colors"
              >
                Admin
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
