package com.mockmate.auth_service.service.room;

import com.mockmate.auth_service.dto.room.RoomResponseDto;

public interface RoomService {
    RoomResponseDto createOrJoinRoom(Long interviewId) ;
}
