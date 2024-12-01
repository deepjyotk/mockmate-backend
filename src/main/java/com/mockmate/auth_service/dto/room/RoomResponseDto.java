package com.mockmate.auth_service.dto.room;


public class RoomResponseDto {

    private String roomId;
    private String kitToken;
    private Long peerId;
    private String peerName;
    private String status;
    private String message;

    // Getters and Setters
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getKitToken() {
        return kitToken;
    }

    public void setKitToken(String kitToken) {
        this.kitToken = kitToken;
    }

    public Long getPeerId() {
        return peerId;
    }

    public void setPeerId(Long peerId) {
        this.peerId = peerId;
    }

    public String getPeerName() {
        return peerName;
    }

    public void setPeerName(String peerName) {
        this.peerName = peerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Builder for easier instantiation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final RoomResponseDto dto;

        public Builder() {
            dto = new RoomResponseDto();
        }

        public Builder roomId(String roomId) {
            dto.setRoomId(roomId);
            return this;
        }

        public Builder kitToken(String kitToken) {
            dto.setKitToken(kitToken);
            return this;
        }

        public Builder peerId(Long peerId) {
            dto.setPeerId(peerId);
            return this;
        }

        public Builder peerName(String peerName) {
            dto.setPeerName(peerName);
            return this;
        }

        public Builder status(String status) {
            dto.setStatus(status);
            return this;
        }

        public Builder message(String message) {
            dto.setMessage(message);
            return this;
        }

        public RoomResponseDto build() {
            return dto;
        }
    }
}
