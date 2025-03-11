package com.mockmate.auth_service.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String message;  // A success message
    private String username; // The username of the logged-in user
    private String email ;
}
