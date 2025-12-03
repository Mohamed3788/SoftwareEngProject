import { useEffect, useState } from "react";
import {
  getTodoTasks,
  createTodoTask,
  updateTodoTask,
  toggleTodoTask,
  deleteTodoTask,
} from "../../api/todo";

export default function StudentTodoPage() {
  const [todos, setTodos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingTodo, setEditingTodo] = useState(null);
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    dueDate: "",
  });

  const loadTodos = async () => {
    try {
      const res = await getTodoTasks();
      setTodos(res.data);
    } catch (err) {
      console.error("Failed to load todos:", err);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadTodos();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.title.trim()) {
      alert("Please enter a title");
      return;
    }

    try {
      if (editingTodo) {
        await updateTodoTask(editingTodo.id, formData);
      } else {
        await createTodoTask(formData);
      }
      setFormData({ title: "", description: "", dueDate: "" });
      setShowForm(false);
      setEditingTodo(null);
      loadTodos();
    } catch (err) {
      console.error("Failed to save todo:", err);
      alert("Failed to save todo task");
    }
  };

  const handleToggle = async (id) => {
    try {
      await toggleTodoTask(id);
      loadTodos();
    } catch (err) {
      console.error("Failed to toggle todo:", err);
    }
  };

  const handleDelete = async (id) => {
    if (!confirm("Are you sure you want to delete this task?")) return;

    try {
      await deleteTodoTask(id);
      loadTodos();
    } catch (err) {
      console.error("Failed to delete todo:", err);
      alert("Failed to delete todo task");
    }
  };

  const handleEdit = (todo) => {
    setEditingTodo(todo);
    setFormData({
      title: todo.title,
      description: todo.description || "",
      dueDate: todo.dueDate || "",
    });
    setShowForm(true);
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingTodo(null);
    setFormData({ title: "", description: "", dueDate: "" });
  };

  if (loading) return <div className="p-6">Loading...</div>;

  const completedTodos = todos.filter((t) => t.completed);
  const pendingTodos = todos.filter((t) => !t.completed);

  return (
    <div className="p-6 max-w-5xl mx-auto">
      <div className="flex justify-between items-center mb-8">
        <h1 className="page-title">My To-Do List</h1>
        <button
          onClick={() => setShowForm(!showForm)}
          className="btn-primary"
        >
          {showForm ? "Cancel" : "+ New Task"}
        </button>
      </div>

      {/* Create/Edit Form */}
      {showForm && (
        <div className="card mb-6 bg-gradient-to-r from-blue-50 to-purple-50 border-2 border-blue-200">
          <h3 className="text-xl font-bold mb-4 text-blue-800">
            {editingTodo ? "Edit Task" : "Create New Task"}
          </h3>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block font-semibold mb-1 text-gray-700">
                Title *
              </label>
              <input
                type="text"
                className="input-field"
                value={formData.title}
                onChange={(e) =>
                  setFormData({ ...formData, title: e.target.value })
                }
                placeholder="e.g., Complete Math Homework"
                required
              />
            </div>

            <div>
              <label className="block font-semibold mb-1 text-gray-700">
                Description
              </label>
              <textarea
                className="input-field"
                rows="3"
                value={formData.description}
                onChange={(e) =>
                  setFormData({ ...formData, description: e.target.value })
                }
                placeholder="Add details about this task..."
              />
            </div>

            <div>
              <label className="block font-semibold mb-1 text-gray-700">
                Due Date
              </label>
              <input
                type="date"
                className="input-field"
                value={formData.dueDate}
                onChange={(e) =>
                  setFormData({ ...formData, dueDate: e.target.value })
                }
              />
            </div>

            <div className="flex gap-3">
              <button type="submit" className="btn-primary">
                {editingTodo ? "Update Task" : "Create Task"}
              </button>
              <button
                type="button"
                onClick={handleCancel}
                className="btn-secondary"
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Pending Tasks */}
      <div className="mb-8">
        <h2 className="section-title flex items-center gap-2">
          <span className="text-orange-600">⏳</span> Pending Tasks
          <span className="text-sm font-normal text-gray-500">
            ({pendingTodos.length})
          </span>
        </h2>
        {pendingTodos.length === 0 ? (
          <p className="text-gray-500 italic p-4 bg-gray-50 rounded">
            No pending tasks. Great job! 🎉
          </p>
        ) : (
          <div className="space-y-3">
            {pendingTodos.map((todo) => (
              <TodoCard
                key={todo.id}
                todo={todo}
                onToggle={handleToggle}
                onEdit={handleEdit}
                onDelete={handleDelete}
              />
            ))}
          </div>
        )}
      </div>

      {/* Completed Tasks */}
      <div>
        <h2 className="section-title flex items-center gap-2">
          <span className="text-green-600">✓</span> Completed Tasks
          <span className="text-sm font-normal text-gray-500">
            ({completedTodos.length})
          </span>
        </h2>
        {completedTodos.length === 0 ? (
          <p className="text-gray-500 italic p-4 bg-gray-50 rounded">
            No completed tasks yet.
          </p>
        ) : (
          <div className="space-y-3">
            {completedTodos.map((todo) => (
              <TodoCard
                key={todo.id}
                todo={todo}
                onToggle={handleToggle}
                onEdit={handleEdit}
                onDelete={handleDelete}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

function TodoCard({ todo, onToggle, onEdit, onDelete }) {
  const isOverdue =
    !todo.completed &&
    todo.dueDate &&
    new Date(todo.dueDate) < new Date();

  return (
    <div
      className={`card hover:shadow-lg transition-all ${
        todo.completed
          ? "bg-green-50 border-green-200 opacity-75"
          : isOverdue
          ? "bg-red-50 border-red-200"
          : "bg-white border-gray-200"
      }`}
    >
      <div className="flex items-start gap-4">
        {/* Checkbox */}
        <input
          type="checkbox"
          checked={todo.completed}
          onChange={() => onToggle(todo.id)}
          className="mt-1 w-5 h-5 cursor-pointer accent-green-600"
        />

        {/* Content */}
        <div className="flex-1">
          <h3
            className={`text-lg font-bold mb-1 ${
              todo.completed
                ? "line-through text-gray-500"
                : "text-gray-800"
            }`}
          >
            {todo.title}
          </h3>
          {todo.description && (
            <p
              className={`text-sm mb-2 ${
                todo.completed ? "text-gray-400" : "text-gray-600"
              }`}
            >
              {todo.description}
            </p>
          )}
          {todo.dueDate && (
            <div className="flex items-center gap-2 text-sm">
              <span className="font-semibold">Due:</span>
              <span
                className={`${
                  isOverdue
                    ? "text-red-600 font-bold"
                    : todo.completed
                    ? "text-gray-400"
                    : "text-blue-600"
                }`}
              >
                {new Date(todo.dueDate).toLocaleDateString()}
                {isOverdue && " (Overdue!)"}
              </span>
            </div>
          )}
        </div>

        {/* Actions */}
        <div className="flex gap-2">
          <button
            onClick={() => onEdit(todo)}
            className="px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600 transition text-sm font-medium"
          >
            Edit
          </button>
          <button
            onClick={() => onDelete(todo.id)}
            className="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition text-sm font-medium"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
}
