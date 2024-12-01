package com.mockmate.auth_service.service.room;

import com.mockmate.auth_service.controller.ZegoController;
import com.mockmate.auth_service.dto.room.RoomResponseDto;
import com.mockmate.auth_service.entities.interview.InterviewSlot;
import com.mockmate.auth_service.entities.interview.UpcomingInterviews;
import com.mockmate.auth_service.enums.InterviewStatusEnum;
import com.mockmate.auth_service.exception.custom.NoPeerMatchedYetException;
import com.mockmate.auth_service.exception.custom.PeerWaitingThresholdReachedException;
import com.mockmate.auth_service.exception.custom.ResourceNotFoundException;
import com.mockmate.auth_service.exception.custom.WaitingForPeerException;
import com.mockmate.auth_service.repository.interview.InterviewSlotRepository;
import com.mockmate.auth_service.repository.interview.InterviewStatusRepository;
import com.mockmate.auth_service.repository.interview.UpcomingInterviewRepository;
import com.mockmate.auth_service.service.scheduler.SchedulerService;
import com.mockmate.auth_service.service.zego.ZegoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomServiceImpl  implements RoomService{
    private final SimpMessagingTemplate messagingTemplate;
    private final UpcomingInterviewRepository upcomingInterviewRepository;
    private final InterviewSlotRepository interviewSlotRepository ;
    private final InterviewStatusRepository interviewStatusRepository;
    private final SchedulerService schedulerService;

    private final ZegoService zegoService; // Service layer for Zego logic

    // To keep track of pending pairings
    private final ConcurrentHashMap<Long, Long> pendingPairings = new ConcurrentHashMap<>();

    @Autowired
    public RoomServiceImpl(SimpMessagingTemplate messagingTemplate,
                           UpcomingInterviewRepository upcomingInterviewRepository,
                           InterviewSlotRepository interviewSlotRepository ,
                           InterviewStatusRepository interviewStatusRepository,
                           ZegoController zegoController,
                           SchedulerService schedulerService, ZegoService zegoService) {
        this.messagingTemplate = messagingTemplate;
        this.upcomingInterviewRepository = upcomingInterviewRepository;
        this.interviewSlotRepository = interviewSlotRepository ;
        this.interviewStatusRepository = interviewStatusRepository;
        this.schedulerService = schedulerService;
        this.zegoService = zegoService ;
    }

        @Transactional
        private void joinWaitingRoom(Long interviewId, String username) {
            // Step 1: Update the interview status to WAITING_TO_JOIN
            UpcomingInterviews currentInterview = upcomingInterviewRepository.findById(interviewId)
                    .orElseThrow(() -> new RuntimeException("Upcoming interview not found with ID: " + interviewId));

            String waitingStatus = InterviewStatusEnum.WAITING_TO_JOIN.getValue();
            currentInterview.setStatus(interviewStatusRepository.findByStatusName(waitingStatus)
                    .orElseThrow(() -> new RuntimeException("Interview status not found: " + waitingStatus)));

            upcomingInterviewRepository.save(currentInterview);
        }

        @Transactional
        public RoomResponseDto createOrJoinRoom(Long interviewId) {
            // Step 1: Fetch the interview and update the status to WAITING_TO_JOIN
            UpcomingInterviews upcomingInterview = upcomingInterviewRepository.findById(interviewId)
                    .orElseThrow(() -> new RuntimeException("Upcoming interview not found with ID: " + interviewId));

            String waitingToJoinStatus = InterviewStatusEnum.WAITING_TO_JOIN.getValue();
            var status = interviewStatusRepository.findByStatusName(waitingToJoinStatus)
                    .orElseThrow(() -> new RuntimeException("Interview status not found: " + waitingToJoinStatus));

            upcomingInterview.setStatus(status);
            upcomingInterviewRepository.save(upcomingInterview);

            // Step 2: Check the peer interview
            Optional<UpcomingInterviews> peerInterviewOpt = Optional.ofNullable(upcomingInterview.getPeerInterview());

            if (peerInterviewOpt.isPresent()) {
                UpcomingInterviews peerInterview = peerInterviewOpt.get();

                // If peer status is also WAITING_TO_JOIN, create the room and notify users
                if (peerInterview.getStatus().getStatusName().equals(waitingToJoinStatus)) {
                    String roomId = generateRoomId(upcomingInterview.getUpcomingInterviewId(), peerInterview.getUpcomingInterviewId());
                    // Generate tokens for both users
                    String userToken = zegoService.generateKitToken(roomId,
                            upcomingInterview.getUserId().toString(),
                            "User_" + upcomingInterview.getUserId()); // Replace with actual user name if available

                    String peerToken = zegoService.generateKitToken(roomId,
                            upcomingInterview.getUserId().toString(),
                            "User_" + peerInterview.getUserId()); // Replace with actual peer name if available

                    return RoomResponseDto.builder()
                            .roomId(roomId)
                            .kitToken(userToken)
                            .peerId(peerInterview.getUserId())
                            .peerName("PlaceholderPeerName") // Replace with actual peer name from DB
                            .status("Waiting")
                            .message("Room created successfully. Notify peer.")
                            .build();
                } else {

                    int THRESHOLD_WAIT_IN_SECONDS = 60 ;

                    Long slotId = upcomingInterview.getSlotId() ;

                    InterviewSlot interviewSlot = interviewSlotRepository.findBySlotId(slotId).orElseThrow(() ->new ResourceNotFoundException("InterviewSlot with slotId " + slotId + " not found")) ;

                    String timInUtc  = interviewSlot.getInterviewTypeTime().getTime() ;

                    OffsetTime acutalInterviewTime = OffsetTime.parse(timInUtc, DateTimeFormatter.ISO_OFFSET_TIME);
                    OffsetTime requestMadeTime = OffsetTime.now(ZoneOffset.UTC);
                    long differenceInSeconds = Duration.between(requestMadeTime, acutalInterviewTime).getSeconds();

                    // Check the conditions
                    if (Math.abs(differenceInSeconds) > THRESHOLD_WAIT_IN_SECONDS) {
                        upcomingInterview.setQuestionIDForPeer(null);
                        upcomingInterview.setPeerInterview(null);
                        upcomingInterviewRepository.save(upcomingInterview) ;

                        // Difference is greater than threshold...let this user be waiting be free from this peer..and open for
                        //schdeduler to match with some other peer.

                        throw new PeerWaitingThresholdReachedException("The peer has been waiting too long (over 1 minute).");
                    } else {
                        // Difference is within the threshold
                        throw new WaitingForPeerException("The peer is still waiting, but within the acceptable time range.");
                    }
                }
            } else {
                throw new NoPeerMatchedYetException("NO PEER MATCHED");
            }
        }

        private String generateRoomId(Long interviewId, Long peerInterviewId) {
            // Combine interview IDs to create a unique room ID
            return "room_" + interviewId + "_" + peerInterviewId;
        }

        private void scheduleFallbackForUser(UpcomingInterviews upcomingInterview) {
            // Clear peer-related details and update status to UPCOMING
            var upcomingStatus = interviewStatusRepository.findByStatusName(InterviewStatusEnum.UPCOMING.getValue())
                    .orElseThrow(() -> new RuntimeException("Interview status not found: " + InterviewStatusEnum.UPCOMING.getValue()));

            upcomingInterview.setPeerInterview(null);
            upcomingInterview.setQuestionIDForPeer(null);
            upcomingInterview.setStatus(upcomingStatus);
            upcomingInterviewRepository.save(upcomingInterview);
     }
}
