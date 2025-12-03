# WebSocket Discussion - Debugging Steps

## The Issue
Messages are being sent successfully but not appearing in the discussion.

## Root Cause Found
The `@SendTo` annotation doesn't support path variables, so messages were being broadcast to the wrong topic.

## Fix Applied
Changed from `@SendTo("/topic/courses/{courseId}")` to manually broadcasting with `SimpMessagingTemplate`.

## Testing Steps

### Step 1: Restart Backend
Restart your Spring Boot application.

### Step 2: Test WebSocket Broadcast (Bypass Discussion Logic)

Open a new terminal and run this curl command to test if broadcasting works:

```bash
curl -X POST http://localhost:8080/api/test/broadcast/1 \
  -H "Content-Type: application/json" \
  -d "\"Hello from test endpoint\""
```

Replace `1` with your actual course ID.

**What to expect:**
- Backend console should show:
  ```
  === TEST BROADCAST ===
  Course ID: 1
  Message: Hello from test endpoint
  Broadcasting to: /topic/courses/1
  ✅ Test broadcast complete
  ```

- Frontend console (if you're on the discussion page for course 1) should show:
  ```
  📨 Received message from server: {id: 999, content: "TEST: Hello from test endpoint", ...}
  ✅ Adding new message to state
  ```

- The message should appear in the discussion UI

### Step 3: If Test Works, Try Real Message

If the test broadcast works, the issue is in the discussion controller. If it doesn't work, the issue is with the WebSocket subscription.

### Step 4: Check Frontend Logs

When you load the discussion page, you should see:
```
🔍 Course ID from URL: 1
🔍 Course ID type: string
Token present: true
[STOMP Debug] ... connection details ...
✅ Connected to WebSocket
Subscribing to: /topic/courses/1
✅ Subscribed successfully
```

### Step 5: Send a Real Message

After sending a message through the UI, check:

**Backend logs should show:**
```
WebSocket Message - Command: SEND
SEND - Destination: /app/courses/1/send
SEND - User: teacher@example.com

=== WebSocket Message Received ===
Course ID: 1
Content: Your message
Sender: teacher@example.com (ID: 1)
DiscussionService.saveMessage - Course ID: 1, Sender: teacher@example.com
Message saved to DB - ID: 123
Returning response: DiscussionMessageResponse{...}
Response created - Message ID: 123
Broadcasting to: /topic/courses/1
✅ Message broadcast complete
=== End WebSocket Message ===
```

**Frontend logs should show:**
```
📤 Sending message: {content: "Your message", courseId: 1}
Destination: /app/courses/1/send
✅ Message sent successfully

📨 Received message from server: {id: 123, content: "Your message", ...}
✅ Adding new message to state
```

## Common Issues

### Issue 1: "SEND - User: null"
**Problem:** Authentication failed
**Solution:** Check that the Authorization header is being sent in the CONNECT frame

### Issue 2: No "📨 Received message" in frontend
**Problem:** Message not being broadcast or subscription failed
**Possible causes:**
- Topic mismatch (check the exact topic in "Broadcasting to:" vs "Subscribing to:")
- Message broker not configured correctly
- Exception thrown before broadcast

### Issue 3: "Broadcasting to: /topic/courses/undefined"
**Problem:** courseId is undefined
**Solution:** Check that @DestinationVariable is correctly extracting the course ID

## Quick Test Command

To quickly test if a message appears, run this in your browser console while on the discussion page:

```javascript
// This should show the course ID
console.log("Current course ID:", window.location.pathname.match(/\/courses\/(\d+)/)?.[1]);
```

Then use that course ID in the curl command above.
