import { useEffect, useState } from "react";
import useAuth from "../../../hooks/useAuth";
import {
  getStudentModules,
  getTeacherModules,
  createModule,
  addModuleItem,
} from "../../../api/modules";
import { useParams } from "react-router-dom";

export default function Modules() {
  const { id: courseId } = useParams();
  const auth = useAuth();
  const role = auth?.role;

  const [modules, setModules] = useState([]);
  const [loading, setLoading] = useState(true);

  // Teacher form states
  const [newModuleTitle, setNewModuleTitle] = useState("");
  const [newItem, setNewItem] = useState({
    type: "TEXT",
    content: "",
    file: null,
  });

  const loadModules = async () => {
    try {
      const res =
        role === "TEACHER"
          ? await getTeacherModules(courseId)
          : await getStudentModules(courseId);

      setModules(res.data);
    } catch (err) {
      console.error("Failed to load modules:", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadModules();
  }, [courseId]);

  if (loading) return <div>Loading modules...</div>;

  // 🔵 Teacher: Create new module
  const handleCreateModule = async () => {
    if (!newModuleTitle.trim()) return;

    try {
      await createModule({
        title: newModuleTitle,
        courseId: Number(courseId),
      });
      setNewModuleTitle("");
      loadModules();
    } catch (err) {
      console.error("Create module failed:", err);
    }
  };

  // 🔵 Teacher: Add module item
  const handleAddModuleItem = async (moduleId) => {
    if (newItem.type === "TEXT" && !newItem.content.trim()) {
      alert("Please enter content for text items");
      return;
    }
    if (newItem.type === "FILE" && !newItem.file) {
      alert("Please select a file for file items");
      return;
    }

    try {
      console.log("Adding module item:", { moduleId, type: newItem.type, hasFile: !!newItem.file });
      await addModuleItem(moduleId, {
        type: newItem.type,
        content: newItem.type === "TEXT" ? newItem.content : "",
        file: newItem.type === "FILE" ? newItem.file : null,
      });

      setNewItem({ type: "TEXT", content: "", file: null });
      loadModules();
      alert("Module item added successfully!");
    } catch (err) {
      console.error("Add item failed:", err);
      const errorMsg = err.response?.data?.message || err.response?.data?.error || err.message || "Unknown error";
      alert("Failed to add item: " + errorMsg);
      if (err.response?.status === 403) {
        console.error("403 Forbidden - Check if you're the teacher of this course");
      }
    }
  };

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6 text-gray-800">Modules</h2>

      {/* ============ TEACHER: CREATE MODULE SECTION ============ */}
      {role === "TEACHER" && (
        <div className="mb-6 p-5 bg-gradient-to-r from-purple-50 to-pink-50 rounded-lg shadow-md border border-purple-200">
          <h3 className="font-bold text-lg mb-3 text-purple-700">Create New Module</h3>

          <input
            placeholder="Module Title"
            className="border p-2 w-full mb-2"
            value={newModuleTitle}
            onChange={(e) => setNewModuleTitle(e.target.value)}
          />

          <button
            className="bg-gradient-to-r from-purple-600 to-purple-700 text-white px-6 py-2 rounded-lg font-semibold hover:from-purple-700 hover:to-purple-800 transition-all shadow-md"
            onClick={handleCreateModule}
          >
            Add Module
          </button>
        </div>
      )}

      {/* ============ MODULE LIST ============ */}
      <div className="space-y-4">
        {modules.map((m) => (
          <div
            key={m.id}
            className="border border-gray-200 p-5 rounded-lg bg-white shadow-md hover:shadow-lg transition-shadow mb-4"
          >
            <h3 className="text-lg font-semibold mb-3">{m.title}</h3>

            {/* Module Items */}
            <div className="ml-4 space-y-2">
              {m.items?.length === 0 && (
                <div className="text-gray-500 text-sm">
                  No items yet.
                </div>
              )}

              {m.items?.map((item) => (
                <div
                  key={item.id}
                  className="border-l pl-3 pb-1"
                >
                  <div className="text-sm">
                    <span className="font-semibold uppercase text-blue-600">
                      {item.type}
                    </span>{" "}
                    — Position {item.position}
                  </div>

                  {/* TEXT ITEM */}
                  {item.type === "TEXT" && (
                    <div className="text-gray-700 text-sm">
                      {item.content}
                    </div>
                  )}

                  {/* FILE ITEM */}
                  {item.type === "FILE" && (
                    <a
                      href={item.fileUrl}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="text-blue-500 underline text-sm"
                    >
                      Download File
                    </a>
                  )}
                </div>
              ))}
            </div>

            {/* ============ TEACHER: ADD ITEM FORM ============ */}
            {role === "TEACHER" && (
              <div className="mt-4 p-4 bg-gradient-to-r from-purple-50 to-pink-50 rounded-lg border border-purple-200">
                <h4 className="font-semibold mb-3 text-purple-700">Add Item to Module</h4>

                <select
                  className="border p-2 w-full mb-2"
                  value={newItem.type}
                  onChange={(e) =>
                    setNewItem({ ...newItem, type: e.target.value })
                  }
                >
                  <option value="TEXT">TEXT</option>
                  <option value="FILE">FILE</option>
                </select>

                {/* TEXT */}
                {newItem.type === "TEXT" && (
                  <textarea
                    placeholder="Text content..."
                    className="border p-2 w-full mb-2"
                    value={newItem.content}
                    onChange={(e) =>
                      setNewItem({ ...newItem, content: e.target.value })
                    }
                  />
                )}

                {/* FILE */}
                {newItem.type === "FILE" && (
                  <input
                    type="file"
                    className="border p-2 w-full mb-2"
                    onChange={(e) =>
                      setNewItem({ ...newItem, file: e.target.files[0] || null })
                    }
                  />
                )}

                <button
                  className="bg-gradient-to-r from-green-600 to-green-700 text-white px-6 py-2 rounded-lg font-semibold hover:from-green-700 hover:to-green-800 transition-all shadow-md"
                  onClick={() => handleAddModuleItem(m.id)}
                >
                  Add Item
                </button>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
