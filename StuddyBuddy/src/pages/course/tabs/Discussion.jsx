import { useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { useParams } from "react-router-dom";
import { fetchDiscussionHistory } from "../../../api/discussion";
import useAuth from "../../../hooks/useAuth";

export default function Discussion() {
  const { id: courseId } = useParams();
  const auth = useAuth();
  const user = auth?.user;

  const [messages, setMessages] = useState([]);
  const [content, setContent] = useState("");
  const stompClient = useRef(null);
  const bottomRef = useRef(null);

  // Auto-scroll
  const scrollToBottom = () => {
    bottomRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  // Load history
  const loadHistory = async () => {
    try {
      const res = await fetchDiscussionHistory(courseId);
      setMessages(res.data);
      setTimeout(scrollToBottom, 50);
    } catch (err) {
      console.error("Error loading history:", err);
    }
  };

  // WebSocket connection
  useEffect(() => {
    console.log("🔍 Course ID from URL:", courseId);
    console.log("🔍 Course ID type:", typeof courseId);

    loadHistory();

    const socket = new SockJS("http://localhost:8080/ws");
    const stomp = Stomp.over(socket);
    stompClient.current = stomp;

    // Enable debug logging temporarily
    stomp.debug = (str) => {
      console.log('[STOMP Debug]', str);
    };

    let subscription = null;

    // Get token for authentication
    const token = localStorage.getItem("token");
    console.log("Token present:", !!token);
    const headers = token ? { Authorization: `Bearer ${token}` } : {};

    stomp.connect(
      headers,
      () => {
        console.log("✅ Connected to WebSocket");
        const subscribeTopic = `/topic/courses/${courseId}`;
        console.log("Subscribing to:", subscribeTopic);
        subscription = stomp.subscribe(subscribeTopic, (msg) => {
          try {
            const parsed = JSON.parse(msg.body);
            console.log("📨 Received message from server:", parsed);
            setMessages((prev) => {
              // Avoid duplicates
              if (prev.some((m) => m.id === parsed.id)) {
                console.log("⚠️ Duplicate message, skipping");
                return prev;
              }
              console.log("✅ Adding new message to state");
              return [...prev, parsed];
            });
            setTimeout(scrollToBottom, 50);
          } catch (err) {
            console.error("❌ Error parsing message:", err);
          }
        });
        console.log("✅ Subscribed successfully");
      },
      (error) => {
        console.error("❌ WebSocket connection error:", error);
      }
    );

    return () => {
      if (subscription) {
        subscription.unsubscribe();
        console.log("Unsubscribed from WebSocket");
      }
      if (stomp && stomp.connected) {
        stomp.disconnect();
        console.log("Disconnected from WebSocket");
      }
    };
  }, [courseId]);

  // Send message
  const sendMessage = () => {
    if (!content.trim()) return;

    if (!stompClient.current || !stompClient.current.connected) {
      console.error("WebSocket not connected");
      alert("Connection lost. Please refresh the page.");
      return;
    }

    const body = {
      content,
      courseId: Number(courseId),
    };

    console.log("📤 Sending message:", body);
    console.log("Destination:", `/app/courses/${courseId}/send`);

    try {
      stompClient.current.send(
        `/app/courses/${courseId}/send`,
        {},
        JSON.stringify(body)
      );
      console.log("✅ Message sent successfully");
      setContent("");
    } catch (err) {
      console.error("❌ Failed to send message:", err);
      alert("Failed to send message. Please try again.");
    }
  };

  return (
    <div className="flex flex-col h-[75vh]">
      <h2 className="text-2xl font-bold mb-4 text-gray-800">Discussion</h2>

      {/* Messages */}
      <div className="flex-1 overflow-y-auto bg-gradient-to-b from-white to-gray-50 p-4 rounded-lg border border-gray-200 shadow-inner">
        {messages.map((m) => (
          <div
            key={m.id}
            className={`mb-4 p-3 rounded max-w-[70%] ${user && m.senderId === user.id
                ? "bg-blue-600 text-white ml-auto"
                : "bg-white border"
              }`}
          >
            <div className="font-semibold text-sm">{m.senderName}</div>
            <div>{m.content}</div>
            <div className="text-xs text-gray-300 mt-1">
              {new Date(m.timestamp).toLocaleString()}
            </div>
          </div>
        ))}

        <div ref={bottomRef}></div>
      </div>

      {/* Input */}
      <div className="flex mt-4">
        <input
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="Write a message..."
          className="flex-1 border rounded px-3 py-2 mr-2"
        />

        <button
          onClick={sendMessage}
          className="px-6 py-2 bg-gradient-to-r from-blue-600 to-blue-700 text-white rounded-lg font-semibold hover:from-blue-700 hover:to-blue-800 transition-all shadow-md"
        >
          Send
        </button>
      </div>
    </div>
  );
}
