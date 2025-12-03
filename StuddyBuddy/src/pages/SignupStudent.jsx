import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { signupStudent } from "../api/auth";

export default function SignupStudent() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    studentClass: "CPI1"
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await signupStudent({ ...form, role: "STUDENT" });
      navigate("/login");
    } catch (err) {
      console.error("Signup error:", err);
      setError(
        err.response?.data?.message ||
        "Signup failed. Please try again."
      );
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (field, value) => {
    setForm({ ...form, [field]: value });
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-50 to-emerald-100 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full">
        <div className="bg-white rounded-2xl shadow-xl p-8">
          <div className="text-center mb-8">
            <h1 className="text-3xl font-bold text-gray-900 mb-2">Student Signup</h1>
            <p className="text-gray-600">Create your student account</p>
          </div>

          {error && (
            <div className="mb-4 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
              {error}
            </div>
          )}

          <form onSubmit={submit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                First Name
              </label>
              <input
                type="text"
                required
                className="input-field"
                placeholder="John"
                value={form.firstName}
                onChange={(e) => handleChange("firstName", e.target.value)}
                disabled={loading}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Last Name
              </label>
              <input
                type="text"
                required
                className="input-field"
                placeholder="Doe"
                value={form.lastName}
                onChange={(e) => handleChange("lastName", e.target.value)}
                disabled={loading}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Email
              </label>
              <input
                type="email"
                required
                className="input-field"
                placeholder="john.doe@example.com"
                value={form.email}
                onChange={(e) => handleChange("email", e.target.value)}
                disabled={loading}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Password
              </label>
              <input
                type="password"
                required
                className="input-field"
                placeholder="••••••••"
                value={form.password}
                onChange={(e) => handleChange("password", e.target.value)}
                disabled={loading}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Class
              </label>
              <select
                className="input-field"
                value={form.studentClass}
                onChange={(e) => handleChange("studentClass", e.target.value)}
                disabled={loading}
              >
                <option value="CPI1">CPI1</option>
                <option value="CPI2">CPI2</option>
                <option value="CS1">CS1</option>
                <option value="CS2">CS2</option>
                <option value="CS3">CS3</option>
              </select>
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full btn-success disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {loading ? "Creating Account..." : "Create Account"}
            </button>
          </form>

          <div className="mt-6 text-center">
            <Link
              to="/login"
              className="text-sm text-blue-600 hover:text-blue-800 font-medium"
            >
              Already have an account? Sign in
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
