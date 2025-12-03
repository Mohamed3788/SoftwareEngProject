package com.university.lms.StudyBuddy.config;

import com.university.lms.StudyBuddy.auth.service.JwtService;
import com.university.lms.StudyBuddy.user.model.User;
import com.university.lms.StudyBuddy.user.repository.UserRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public WebSocketAuthInterceptor(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null) {
            System.out.println("WebSocket Message - Command: " + accessor.getCommand());

            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                // Extract token from headers
                String authHeader = accessor.getFirstNativeHeader("Authorization");
                if (authHeader == null) {
                    authHeader = accessor.getFirstNativeHeader("authorization");
                }

                System.out.println("CONNECT - Auth header present: " + (authHeader != null));

                if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
                    try {
                        String jwt = authHeader.substring(7);
                        String email = jwtService.extractUsername(jwt);
                        System.out.println("CONNECT - Extracted email: " + email);

                        if (email != null) {
                            Optional<User> userOpt = userRepository.findByEmail(email);

                            if (userOpt.isPresent() && jwtService.isTokenValid(jwt, userOpt.get())) {
                                User user = userOpt.get();
                                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                                        "ROLE_" + user.getRole().name());

                                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                        user, null, List.of(authority));

                                accessor.setUser(authToken);

                                // ✅ CRITICAL FIX: Store user in session for future SEND messages
                                accessor.getSessionAttributes().put("user", user);

                                System.out.println("CONNECT - User authenticated: " + user.getEmail() + " (ID: "
                                        + user.getId() + ")");
                            } else {
                                System.err.println("CONNECT - User not found or token invalid for email: " + email);
                            }
                        }
                    } catch (Exception e) {
                        // Invalid token - continue without authentication
                        System.err.println("WebSocket authentication failed: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("CONNECT - No valid Authorization header found");
                }
            } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                // Log subscription attempts
                String destination = accessor.getDestination();
                System.out.println("📬 SUBSCRIBE - Destination: " + destination);
                System.out.println(
                        "📬 SUBSCRIBE - User: " + (accessor.getUser() != null ? accessor.getUser().getName() : "null"));
                System.out.println("📬 SUBSCRIBE - Session: " + accessor.getSessionId());
            } else if (StompCommand.SEND.equals(accessor.getCommand())) {
                // ✅ CRITICAL FIX: Restore user from session for SEND messages
                if (accessor.getUser() == null) {
                    User user = (User) accessor.getSessionAttributes().get("user");
                    if (user != null) {
                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                user, null, List.of(authority));
                        accessor.setUser(authToken);
                        System.out.println("SEND - Restored user from session: " + user.getEmail() + " (ID: "
                                + user.getId() + ")");
                    } else {
                        System.err.println("SEND - No user in session!");
                    }
                }

                // Log SEND messages to track message flow
                System.out.println("SEND - Destination: " + accessor.getDestination());
                System.out.println(
                        "SEND - User: " + (accessor.getUser() != null ? accessor.getUser().getName() : "null"));
            } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                System.out.println("DISCONNECT - Session: " + accessor.getSessionId());
            }
        }

        return message;
    }
}
