package com.mockmate.auth_service.dto;

import java.awt.*;

public class ChatMessage {
    private String content;
    private String sender;
    private TrayIcon.MessageType type;

    public ChatMessage(String content, String sender, TrayIcon.MessageType type) {
        this.content = content;
        this.sender = sender;
        this.type = type;
    }

    public ChatMessage() {
    }

    public static ChatMessageBuilder builder() {
        return new ChatMessageBuilder();
    }

    public String getContent() {
        return this.content;
    }

    public String getSender() {
        return this.sender;
    }

    public TrayIcon.MessageType getType() {
        return this.type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setType(TrayIcon.MessageType type) {
        this.type = type;
    }

    public static class ChatMessageBuilder {
        private String content;
        private String sender;
        private TrayIcon.MessageType type;

        ChatMessageBuilder() {
        }

        public ChatMessageBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ChatMessageBuilder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public ChatMessageBuilder type(TrayIcon.MessageType type) {
            this.type = type;
            return this;
        }

        public ChatMessage build() {
            return new ChatMessage(this.content, this.sender, this.type);
        }

        public String toString() {
            return "ChatMessage.ChatMessageBuilder(content=" + this.content + ", sender=" + this.sender + ", type=" + this.type + ")";
        }
    }
}
