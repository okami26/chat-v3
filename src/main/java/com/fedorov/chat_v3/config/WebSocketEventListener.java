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

// Аннотация @Component указывает, что данный класс является Spring-компонентом
@Component
@RequiredArgsConstructor // Генерирует конструктор с обязательными аргументами
@Slf4j // Аннотация для логирования
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate; // Шаблон для отправки сообщений

    @Autowired
    private FeedRepository feedRepository; // Репозиторий для сохранения сообщений

    // Обработчик события отключения пользователя
    @EventListener
    public void HandleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // Оборачиваем сообщение в StompHeaderAccessor для получения заголовков
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        // Извлечение имени пользователя из атрибутов сессии
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        // Проверка, что имя пользователя не null
        if (username != null) {
            log.info("User  Disconnected: {}", username); // Логирование отключения пользователя
            // Создание нового сообщения о выходе
            FeedMessage feedMessage = new FeedMessage();
            feedMessage.setContent("Пользователь " + username + " вышел");
            feedMessage.setSender(username);
            feedMessage.setType("LEAVE");
            feedMessage.setDate("12.12.2012 12:12"); // Здесь можно использовать актуальную дату
            feedRepository.save(feedMessage); // Сохранение сообщения в репозитории

            // Отправка сообщения о выходе всем подписчикам
            messagingTemplate.convertAndSend("/topic/feed", feedMessage);
        }
    }

    // Обработчик события подключения пользователя
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        // Оборачиваем сообщение в StompHeaderAccessor для получения заголовков
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Получение атрибутов сессии
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        // Если атрибуты сессии отсутствуют, создаем их
        if (sessionAttributes == null) {
            sessionAttributes = new HashMap<>();
            headerAccessor.setSessionAttributes(sessionAttributes);
        }

        // Извлечение имени пользователя из заголовков
        String username = headerAccessor.getFirstNativeHeader("username");

        // Проверка, что имя пользователя не null
        if (username != null) {
            System.out.println("Пользователь подключен: " + username); // Логирование подключения пользователя
            sessionAttributes.put("username", username); // Сохранение имени пользователя в атрибутах сессии

            log.info("User  Connected: {}", username); // Логирование подключения пользователя
            // Создание нового сообщения о входе
            FeedMessage feedMessage = new FeedMessage();
            feedMessage.setContent("Пользователь " + username + " зашел");
            feedMessage.setSender(username);
            feedMessage.setType("JOIN");
            feedMessage.setDate("12.12.2012 12:12"); // Здесь можно использовать актуальную дату
            feedRepository.save(feedMessage); // Сохранение сообщения в репозитории

            // Отправка сообщения о входе всем подписчикам
            messagingTemplate.convertAndSend("/topic/feed", feedMessage);
        } else {
            System.out.println("Имя пользователя не передано!"); // Логирование отсутствия имени пользователя
        }
    }
}
