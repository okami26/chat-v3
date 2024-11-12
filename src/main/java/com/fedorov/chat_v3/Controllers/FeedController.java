package com.fedorov.chat_v3.Controllers;

import com.fedorov.chat_v3.models.FeedMessage;
import com.fedorov.chat_v3.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

// Аннотация @Controller указывает, что этот класс является контроллером для обработки сообщений WebSocket
@Controller
public class FeedController {

    // Внедрение зависимостей для работы с шаблоном сообщений и сервисом ленты
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private FeedService feedService;

    // Обработка сообщений с маршрутом "/feed.addNote"
    @MessageMapping("/feed.addNote")
    public void addUser (@Payload FeedMessage feedMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Сохраняет имя отправителя в атрибутах сессии
        headerAccessor.getSessionAttributes().put("username", feedMessage.getSender());

        // Выводит информацию о сообщении и заголовках сессии в консоль для отладки
        System.out.println(feedMessage);
        System.out.println(headerAccessor);

        // Определяет динамическую тему для отправки сообщения
        String dynamicTopic = "/topic/" + "feed";

        // Отправляет сообщение на указанную тему, чтобы все подписчики получили его
        messagingTemplate.convertAndSend(dynamicTopic, feedMessage);
    }
}
