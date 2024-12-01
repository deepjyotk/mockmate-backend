package com.mockmate.auth_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PeerUserDto {
    private Long peerUserId;
    private String peerUserName;
}
