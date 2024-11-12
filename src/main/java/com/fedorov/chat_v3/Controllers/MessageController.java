package com.fedorov.chat_v3.Controllers;

// Импорт необходимых классов и пакетов
import com.fedorov.chat_v3.models.Message;
import com.fedorov.chat_v3.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

// Аннотация @Controller указывает, что этот класс является контроллером Spring
@Controller
public class MessageController {

    // Внедрение зависимости SimpMessagingTemplate для отправки сообщений
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Обработка сообщений, отправленных на адрес "/message.addMessageSender"
    @MessageMapping("/message.addMessageSender")
    public void addMessageSender(@Payload Message message) {
        // Создание динамического топика для отправки сообщения
        String dynamicTopic = "/topic/" + "message" + "/" + message.getSender();

        // Отправка сообщения на указанный топик
        messagingTemplate.convertAndSend(dynamicTopic, message);
    }

    // Обработка сообщений, отправленных на адрес "/message.addMessageRecipient"
    @MessageMapping("/message.addMessageRecipient")
    public void addUser (@Payload Message message) {
        // Создание динамического топика для отправки сообщения
        String dynamicTopic = "/topic/" + "message" + "/" + message.getRecipient();

        // Отправка сообщения на указанный топик
        messagingTemplate.convertAndSend(dynamicTopic, message);
    }
}
