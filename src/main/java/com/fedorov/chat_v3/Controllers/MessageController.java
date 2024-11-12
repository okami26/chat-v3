package com.fedorov.chat_v3.Controllers;


import com.fedorov.chat_v3.models.FeedMessage;
import com.fedorov.chat_v3.models.Message;
import com.fedorov.chat_v3.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private FeedService feedService;


    @MessageMapping("/message.addMessageSender")

    public void addMessageSender(@Payload Message message){


        System.out.println("__________________________________________________");
        String dynamicTopic = "/topic/" + "message" + "/" + message.getSender();
        System.out.println(message);
        messagingTemplate.convertAndSend(dynamicTopic, message);

    }
    @MessageMapping("/message.addMessageRecipient")

    public void addUser(@Payload Message message){


        System.out.println("__________________________________________________");
        String dynamicTopic = "/topic/" + "message" + "/" + message.getRecipient();
        System.out.println(message);
        messagingTemplate.convertAndSend(dynamicTopic, message);

    }

}
