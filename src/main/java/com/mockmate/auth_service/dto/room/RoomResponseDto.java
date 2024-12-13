package com.mockmate.auth_service.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDto {

    private String roomIdHash;
    private String kitToken;
    private String peerName;
    private String status;
    private String message;
}
