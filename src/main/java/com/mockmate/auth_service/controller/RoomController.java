package com.mockmate.auth_service.controller;

import com.mockmate.auth_service.dto.ResponseDto;
import com.mockmate.auth_service.dto.room.ChangeInterviewRoleResponseDTO;
import com.mockmate.auth_service.dto.room.ChangeRoleRequestDTO;
import com.mockmate.auth_service.dto.room.GetRoomPayloadDTO;
import com.mockmate.auth_service.dto.room.RoomResponseDto;
import com.mockmate.auth_service.service.room.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Operation(
            summary = "Create or Join Waiting Room",
            description = "Handles creating or joining a waiting room for an interview",
            tags = {"Room"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room created or joined successfully"),
            @ApiResponse(responseCode = "404", description = "Interview or Peer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/join-waiting-room")
    public ResponseEntity<ResponseDto<RoomResponseDto>> joinWaitingRoom(@RequestParam Long interviewId) {
        // Call the service to process room creation or joining
        var response  = roomService.createOrJoinRoom(interviewId) ;
        var res2 = new ResponseDto<RoomResponseDto>(response, HttpStatus.OK);

        return ResponseEntity.ok(res2) ;
    }


    @GetMapping("/getRoomPayloadForInterview/{interviewID}/{roomHash}")
    public ResponseEntity<ResponseDto<GetRoomPayloadDTO> > getRoomPayloadForInterview(
            @PathVariable Long interviewID,
            @PathVariable String roomHash) {
        GetRoomPayloadDTO payload = roomService.getRoomPayloadForInterview(interviewID, roomHash);

        var res2 = new ResponseDto<GetRoomPayloadDTO>(payload, HttpStatus.OK);
        return ResponseEntity.ok(res2);
    }



    // New endpoint for changing interview role
    @Operation(
            summary = "Change Interview Role",
            description = "Changes the role of an interview within a room",
            tags = {"Room"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role changed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request parameters"),
            @ApiResponse(responseCode = "404", description = "Room or Interview not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/changeRole")
    public ResponseEntity<ResponseDto<ChangeInterviewRoleResponseDTO>> changeRole(
            @RequestBody ChangeRoleRequestDTO changeRoleRequestDTO) {
        // Call the service to change the interview role
        var response = roomService.changeRole(changeRoleRequestDTO.getRoomHash(), changeRoleRequestDTO.getInterviewID());
        ResponseDto<ChangeInterviewRoleResponseDTO> resDto = new ResponseDto<ChangeInterviewRoleResponseDTO>(response, HttpStatus.OK);

        return ResponseEntity.ok(resDto);
    }
}
