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

@Controller
public class FeedController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private FeedService feedService;


    @MessageMapping("/feed.addUser")

    public void addUser(@Payload FeedMessage feedMessage, SimpMessageHeaderAccessor headerAccessor){


        headerAccessor.getSessionAttributes().put("username", feedMessage.getSender());
        feedService.createFeed(feedMessage);
        System.out.println(feedMessage);
        System.out.println(headerAccessor);
        String dynamicTopic = "/topic/" + "feed";
        messagingTemplate.convertAndSend(dynamicTopic, feedMessage);

    }

}
