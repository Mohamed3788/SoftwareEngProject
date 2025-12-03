import { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import axiosClient from "../../api/axiosClient";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default function DiscussionPage() {
  const { id: courseId } = useParams();

  const [messages, setMessages] = useState([]);
  const [content, setContent] = useState("");

  const stompClientRef = useRef(null);
  const bottomRef = useRef(null);


  const loadHistory = async () => {
    try {
      const res = await axiosClient.get(`/discussion/${courseId}`);
      setMessages(res.data);
    } catch (err) {
      console.error("Failed to load discussion history:", err);
    }
  };

  
  const scrollToBottom = () => {
    bottomRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(scrollToBottom, [messages]);

 
  useEffect(() => {
    console.log(" Course ID:", courseId);
    loadHistory();

    const socket = new SockJS("http://localhost:8080/ws");
    const stomp = Stomp.over(socket);
    stompClientRef.current = stomp;

    
    stomp.debug = (str) => {
      console.log('[STOMP]', str);
    };

    let subscription = null;

    
    const token = localStorage.getItem("token");
    console.log("Token present:", !!token);
    const headers = token ? { Authorization: `Bearer ${token}` } : {};

    stomp.connect(
      headers,  
      () => {
        console.log(" WebSocket connected");
        const topic = `/topic/courses/${courseId}`;
        console.log(" Subscribing to:", topic);

        subscription = stomp.subscribe(topic, (frame) => {
          try {
            const msg = JSON.parse(frame.body);
            console.log(" Received message:", msg);
            setMessages((prev) => {
              // Avoid duplicates
              if (prev.some((m) => m.id === msg.id)) {
                console.log(" Duplicate message, skipping");
                return prev;
              }
              console.log(" Adding message to state");
              return [...prev, msg];
            });
            setTimeout(scrollToBottom, 50);
          } catch (err) {
            console.error(" Error parsing message:", err);
          }
        });
        console.log(" Subscribed successfully");
      },
      (error) => {
        console.error(" WebSocket connection error:", error);
      }
    );

    return () => {
      if (subscription) {
        subscription.unsubscribe();
        console.log("Unsubscribed from WebSocket");
      }
      if (stompClientRef.current && stompClientRef.current.connected) {
        stompClientRef.current.disconnect();
        console.log("Disconnected from WebSocket");
      }
    };
  }, [courseId]);

  // -----------------------------
  // Send message
  // -----------------------------
  const sendMessage = () => {
    if (!content.trim()) return;

    if (!stompClientRef.current || !stompClientRef.current.connected) {
      console.error("WebSocket not connected");
      alert("Connection lost. Please refresh the page.");
      return;
    }

    const body = {
      content,
      courseId: Number(courseId),
    };

    try {
      console.log("📤 Sending message:", body);
      console.log("📤 Destination:", `/app/courses/${courseId}/send`);
      stompClientRef.current.send(
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

  // Press Enter to send
  const onKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      sendMessage();
    }
  };

  return (
    <div className="flex flex-col h-[80vh] p-6">

      <h1 className="text-2xl font-semibold mb-4">Discussion</h1>

      {/* CHAT BOX */}
      <div className="flex-1 overflow-y-auto bg-white p-4 border rounded shadow-sm">

        {messages.map((msg) => (
          <div
            key={msg.id}
            className="mb-4 p-3 border rounded bg-gray-50"
          >
            <div className="font-semibold">{msg.senderName}</div>
            <div className="text-gray-700">{msg.content}</div>
            <div className="text-xs text-gray-500 mt-1">
              {new Date(msg.timestamp).toLocaleString()}
            </div>
          </div>
        ))}

        <div ref={bottomRef}></div>
      </div>

      {/* INPUT AREA */}
      <div className="mt-4 flex gap-2">
        <input
          className="flex-1 border p-2 rounded"
          placeholder="Write a message…"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          onKeyDown={onKeyDown}
        />
        <button
          className="bg-blue-600 text-white px-4 py-2 rounded"
          onClick={sendMessage}
        >
          Send
        </button>
      </div>

    </div>
  );
}
