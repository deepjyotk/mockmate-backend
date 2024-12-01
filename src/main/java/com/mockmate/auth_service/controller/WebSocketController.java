package com.mockmate.auth_service.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/message") // Matches /app/message
    @SendTo("/topic/messages") // Sends to the broker at /topic/messages
    public String handleMessage(String message) {
        // Process incoming message
        return "Received: " + message;
    }
}