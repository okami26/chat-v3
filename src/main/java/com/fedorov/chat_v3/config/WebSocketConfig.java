package com.fedorov.chat_v3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// Аннотация @Configuration указывает, что этот класс содержит конфигурацию Spring
@Configuration
@EnableWebSocketMessageBroker // Включает поддержку WebSocket с использованием STOMP
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Метод для регистрации STOMP конечных точек
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Регистрация конечной точки WebSocket с использованием SockJS
        registry.addEndpoint("/ws").withSockJS();
    }

    // Метод для настройки брокера сообщений
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Установка префикса для назначения сообщений приложения
        registry.setApplicationDestinationPrefixes("/app");
        // Включение простого брокера сообщений для обработки сообщений, начинающихся с /topic
        registry.enableSimpleBroker("/topic");
    }
}
