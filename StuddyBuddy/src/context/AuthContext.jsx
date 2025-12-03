import { createContext, useState, useEffect } from "react";
import axiosClient from "../api/axiosClient";

export const AuthContext = createContext();

export default function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const stored = localStorage.getItem("user");
    if (stored && stored !== "undefined") {
      const parsed = JSON.parse(stored);
      setUser(parsed);
      setRole(parsed.role);
    }
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    const res = await axiosClient.post("/auth/login", { email, password });


    const user = {
      id: res.data.id,
      firstName: res.data.firstName,
      lastName: res.data.lastName,
      email: res.data.email,
      role: res.data.role,
      studentClass: res.data.studentClass,
    };

    localStorage.setItem("token", res.data.token);
    localStorage.setItem("user", JSON.stringify(user));

    setUser(user);
    setRole(user.role);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUser(null);
    setRole(null);
  };

  return (
    <AuthContext.Provider value={{ user, role, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
}
