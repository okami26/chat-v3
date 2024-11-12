package com.fedorov.chat_v3.config;

import com.fedorov.chat_v3.models.FeedMessage;
import com.fedorov.chat_v3.repositories.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private FeedRepository feedRepository;

    @EventListener
    public void HandleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ){

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            log.info("User Disconnected: {}", username);
            FeedMessage feedMessage = new FeedMessage();
            feedMessage.setContent("Пользователь " + username + " вышел");
            feedMessage.setSender(username);
            feedMessage.setType("LEAVE");
            feedMessage.setDate("12.12.2012 12:12");
            feedRepository.save(feedMessage);

            messagingTemplate.convertAndSend("/topic/feed", feedMessage);
        }

    }
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());


        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if (sessionAttributes == null) {
            sessionAttributes = new HashMap<>();
            headerAccessor.setSessionAttributes(sessionAttributes);
        }


        String username = headerAccessor.getFirstNativeHeader("username");

        if (username != null) {
            System.out.println("Пользователь подключен: " + username);
            sessionAttributes.put("username", username);

            log.info("User Disconnected: {}", username);
            FeedMessage feedMessage = new FeedMessage();
            feedMessage.setContent("Пользователь " + username + " зашел");
            feedMessage.setSender(username);
            feedMessage.setType("JOIN");
            feedMessage.setDate("12.12.2012 12:12");
            feedRepository.save(feedMessage);

            messagingTemplate.convertAndSend("/topic/feed", feedMessage);

        } else {
            System.out.println("Имя пользователя не передано!");
        }
    }
}
