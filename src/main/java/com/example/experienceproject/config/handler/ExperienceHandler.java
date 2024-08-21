package com.example.experienceproject.config.handler;

import com.example.experienceproject.model.NotificationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ExperienceHandler extends AbstractWebSocketHandler {
    private ConcurrentHashMap<String, Set<WebSocketSession>> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String accountId = extractAccountId(session);
        if (accountId != null) {
            sessions.computeIfAbsent(accountId, k -> ConcurrentHashMap.newKeySet()).add(session);
            log.info("New connection established for account: " + accountId);
        } else {
            log.warn("Connection established without accountId");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String accountId = extractAccountId(session);
        if (accountId != null) {
            Set<WebSocketSession> accountSessions = sessions.get(accountId);
            if (accountSessions != null) {
                accountSessions.remove(session);
                log.info("Connection closed for account: " + accountId);
            }
        }
    }

    private String extractAccountId(WebSocketSession session) {
        String path = session.getUri().getPath();
        String[] pathParts = path.split("/");
        if (pathParts.length > 0) {
            return pathParts[pathParts.length - 1];
        }
        return null;
    }

    public void notifyAgentRemoved(String accountId, String experienceId, String agentId) {
        log.info("Notifying agent removal for account: " + accountId);
        Set<WebSocketSession> accountSessions = sessions.getOrDefault(accountId, Collections.emptySet());
        String message = String.format("Agent %s removed from experience %s", agentId, experienceId);
        
        for (WebSocketSession session : accountSessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                    log.info("Notification sent to session for account: " + accountId);
                } catch (IOException e) {
                    log.error("Error sending message to WebSocket session", e);
                }
            }
        }
    }
}