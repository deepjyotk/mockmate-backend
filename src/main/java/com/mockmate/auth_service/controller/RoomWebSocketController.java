//package com.mockmate.auth_service.controller;
//import com.mockmate.auth_service.service.room.RoomService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//import java.security.Principal;
//
//@Controller
//public class RoomWebSocketController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final RoomService roomService;
//
//    @Autowired
//    public RoomWebSocketController(SimpMessagingTemplate messagingTemplate, RoomService roomService) {
//        this.messagingTemplate = messagingTemplate;
//        this.roomService = roomService;
//    }
//
//    /**
//     * Endpoint for users to join the waiting room.
//     * Clients send a message to /app/join with payload containing interviewId.
//     */
//    @MessageMapping("/join")
//    public void joinWaitingRoom(@Payload Long interviewId, Principal principal) {
//        // Assuming principal.getName() returns the authenticated user’s username or ID
//        String username = principal.getName();
////        roomService.joinWaitingRoom(interviewId, username);
//    }
//
//    /**
//     * You can define more WebSocket endpoints as needed.
//     */
//}