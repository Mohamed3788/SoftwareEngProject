import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export default function useAuth() {
  const context = useContext(AuthContext);
  // Return empty object if context is undefined to prevent destructuring errors
  return context || { user: null, role: null, login: null, logout: null, loading: false };
}
