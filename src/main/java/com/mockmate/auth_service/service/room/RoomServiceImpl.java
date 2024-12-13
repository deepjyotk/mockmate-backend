package com.mockmate.auth_service.service.room;

import com.mockmate.auth_service.controller.ZegoController;
import com.mockmate.auth_service.dto.question.QuestionResponseDto;
import com.mockmate.auth_service.dto.room.GetRoomPayloadDTO;
import com.mockmate.auth_service.dto.room.RoomResponseDto;
import com.mockmate.auth_service.entities.UserEntity;
import com.mockmate.auth_service.entities.interview.InterviewSlot;
import com.mockmate.auth_service.entities.interview.UpcomingInterviews;
import com.mockmate.auth_service.entities.questions.Question;
import com.mockmate.auth_service.enums.InterviewStatusEnum;
import com.mockmate.auth_service.exception.custom.*;
import com.mockmate.auth_service.repository.interview.InterviewSlotRepository;
import com.mockmate.auth_service.repository.interview.InterviewStatusRepository;
import com.mockmate.auth_service.repository.interview.UpcomingInterviewRepository;
import com.mockmate.auth_service.repository.interview.UpcomingInterviewUserPreferenceRepository;
import com.mockmate.auth_service.repository.question.QuestionRepository;
import com.mockmate.auth_service.repository.user.UserRepository;
import com.mockmate.auth_service.service.question.QuestionService;
import com.mockmate.auth_service.service.scheduler.SchedulerService;
import com.mockmate.auth_service.service.zego.ZegoService;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class RoomServiceImpl  implements RoomService{
    private final UpcomingInterviewRepository upcomingInterviewRepository;
    private final InterviewSlotRepository interviewSlotRepository ;
    private final InterviewStatusRepository interviewStatusRepository;

    private final QuestionRepository questionRepository ;

    private final ZegoService zegoService;


    private final UserRepository userRepository ;

    private final UpcomingInterviewUserPreferenceRepository upcomingInterviewUserPreferenceRepository ;

    private final QuestionService questionService ;


    @Autowired
    public RoomServiceImpl(
                           UpcomingInterviewRepository upcomingInterviewRepository,
                           InterviewSlotRepository interviewSlotRepository ,
                           InterviewStatusRepository interviewStatusRepository,
                           ZegoController zegoController,
                           SchedulerService schedulerService,
                           ZegoService zegoService,
                           QuestionRepository questionRepository,
                           UserRepository userRepository,
                           UpcomingInterviewUserPreferenceRepository upcomingInterviewUserPreferenceRepository,
                           QuestionService questionService) {
        this.upcomingInterviewRepository = upcomingInterviewRepository;
        this.interviewSlotRepository = interviewSlotRepository ;
        this.interviewStatusRepository = interviewStatusRepository;
        this.zegoService = zegoService ;
        this.questionRepository = questionRepository ;
        this.userRepository = userRepository ;
        this.upcomingInterviewUserPreferenceRepository = upcomingInterviewUserPreferenceRepository ;
        this.questionService = questionService ;
    }


    private String getRoomHashFromInterviewId(Long s1, Long s2){
        if(s2<s1){
            return getRoomHashFromInterviewId(s2, s1) ;
        }
        return DigestUtils.sha256Hex(s1+"_"+s2 ) ;
    }


    void setRoomIdHash(UpcomingInterviews upcomingInterviews, String  roomIdHash ){
        upcomingInterviews.setRoomIDHash(roomIdHash);
    }

        @Transactional
        private  void setInterviewRole(UpcomingInterviews peer1, UpcomingInterviews peer2){
        if(peer1.getUpcomingInterviewId() > peer2.getUpcomingInterviewId()){
            setInterviewRole(peer2, peer1) ;
        }

        peer1.setInterviewerRole(true);
        peer2.setInterviewerRole(false);

    }

        void changeStatus(UpcomingInterviews upcomingInterviews, InterviewStatusEnum interviewStatus ){
            var status = interviewStatusRepository.findByStatusName(interviewStatus.getValue())
                    .orElseThrow(() -> new RuntimeException("Interview status not found: " + interviewStatus.getValue()));

            upcomingInterviews.setStatus(status);
        }

        private long findAbsDifferenceInTimes(OffsetTime time1, OffsetTime time2){
            long diff =  Duration.between(time1, time2).getSeconds();
            return diff;
        }

        private OffsetTime getSlotTime(InterviewSlot slot){
            return OffsetTime.parse(slot.getInterviewTypeTime().getTime() ,
                    DateTimeFormatter.ISO_OFFSET_TIME) ;
        }

        public RoomResponseDto createOrJoinRoom(Long interviewId) {
            int THRESHOLD_WAIT_IN_SECONDS = 60 ;
            int THRESHOLD_WAIT_FOR_POLLING_IN_SECONDS  = 300 ;

            // Step 1: Fetch the interview and update the status to WAITING_TO_JOIN
            UpcomingInterviews upcomingInterview = upcomingInterviewRepository.findById(interviewId)
                    .orElseThrow(() -> new RuntimeException("Upcoming interview not found with ID: " + interviewId));

            var roomIdOptional = Optional.ofNullable(upcomingInterview.getRoomIDHash());

            //check if already in Progress: Directly return the roomI
            if(roomIdOptional.isPresent() || upcomingInterview.getStatus().getStatusName().equals(InterviewStatusEnum.IN_PROGRESS.getValue())){
                String roomIDHash = upcomingInterview.getRoomIDHash() ;

                return new RoomResponseDto(
                        roomIDHash,
                        "mightDeleteThis",
                        "PlaceholderPeerName", // Replace with actual peer name from DB
                        "Waiting",
                        "Room created successfully. Notify peer."
                );
            }
            changeStatus(upcomingInterview, InterviewStatusEnum.WAITING_TO_JOIN);
            upcomingInterviewRepository.save(upcomingInterview);


            InterviewSlot interviewSlot = interviewSlotRepository.findBySlotId(upcomingInterview.getSlotId() ).orElseThrow(() ->new ResourceNotFoundException("InterviewSlot with slotId " + upcomingInterview.getSlotId()  + " not found")) ;

            long differenceInSeconds  = findAbsDifferenceInTimes(getSlotTime(interviewSlot), OffsetTime.now(ZoneOffset.UTC) );




            // Step 2: Check the peer interview
            Optional<UpcomingInterviews> peerInterviewOpt = Optional.ofNullable(upcomingInterview.getPeerInterview());

            if (peerInterviewOpt.isPresent()) {
                UpcomingInterviews peerInterview = peerInterviewOpt.get();



                // If peer status is also WAITING_TO_JOIN OR IN_PROGRESS, create the room and notify users
                if (peerInterview.getStatus().getStatusName().equals(InterviewStatusEnum.WAITING_TO_JOIN.getValue())) {
                    String roomHash = getRoomHashFromInterviewId(upcomingInterview.getUpcomingInterviewId()  , peerInterview.getUpcomingInterviewId());




                    changeStatus(upcomingInterview, InterviewStatusEnum.IN_PROGRESS);
                    changeStatus(peerInterview, InterviewStatusEnum.IN_PROGRESS);
                    setRoomIdHash(upcomingInterview, roomHash);
                    setRoomIdHash(peerInterview, roomHash);
                    setInterviewRole(upcomingInterview, peerInterview) ;


                    upcomingInterviewRepository.save(upcomingInterview);
                    upcomingInterviewRepository.save(peerInterview);

                    return new RoomResponseDto(
                            roomHash,
                            "mightDeleteThis",
                            "PlaceholderPeerName", // Replace with actual peer name from DB
                            "Waiting",
                            "Room created successfully. Notify peer."
                    );


                } else {
                    // Check the conditions
                    if (differenceInSeconds>0 && differenceInSeconds > THRESHOLD_WAIT_IN_SECONDS) {
                        upcomingInterview.setQuestionIDForPeer(null);
                        upcomingInterview.setPeerInterview(null);
                        upcomingInterviewRepository.save(upcomingInterview) ;

                        throw new PeerWaitingThresholdReachedException("The peer has been waiting too long (over 1 minute).");
                    } else {
                        // Difference is within the threshold
                        throw new WaitingForPeerException("The peer is still waiting, but within the acceptable time range.");
                    }
                }
            } else {
                if(differenceInSeconds>0 && Math.abs(differenceInSeconds) > THRESHOLD_WAIT_FOR_POLLING_IN_SECONDS){
                    upcomingInterviewRepository.delete(upcomingInterview) ;
                    throw new CouldNotMatchInterviewFailureException("NO PEER MATCHED");
                }else{
                    throw new NoPeerMatchedYetException("NO PEER MATCHED");
                }
            }
        }

    @Override
    public GetRoomPayloadDTO getRoomPayloadForInterview(Long interviewID, String roomHash) {
        // Fetch the upcomingInterview
        UpcomingInterviews upcomingInterview = upcomingInterviewRepository.findById(interviewID)
                .orElseThrow(() -> new ResourceNotFoundException("Upcoming interview not found with ID: " + interviewID));

        // Verify that the roomHash matches the stored roomIDHash
        if (!roomHash.equals(upcomingInterview.getRoomIDHash())) {
            throw new IllegalArgumentException("Provided roomHash does not match the interview's room hash.");
        }

        // Fetch the peerInterview
        UpcomingInterviews peerInterview = upcomingInterview.getPeerInterview();
        if (peerInterview == null) {
            throw new ResourceNotFoundException("Peer interview not found for interview ID: " + interviewID);
        }


        String expectedRoomHash =  getRoomHashFromInterviewId(upcomingInterview.getUpcomingInterviewId(), peerInterview.getUpcomingInterviewId()) ;

        // Verify that the provided roomHash matches the expected roomHash
        if (!roomHash.equals(expectedRoomHash)) {
            throw new IllegalArgumentException("Provided roomHash does not match the expected room hash.");
        }

        // Proceed to build the payload as before
        // Fetch user details
        UserEntity user = userRepository.findById(upcomingInterview.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + upcomingInterview.getUserId()));

        UserEntity peerUser = userRepository.findById(peerInterview.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Peer user not found with ID: " + peerInterview.getUserId()));

        // Determine roles
        String userRole = upcomingInterview.isInterviewerRole() ? "Interviewer" : "Interviewee";
        String peerRole = peerInterview.isInterviewerRole() ? "Interviewer" : "Interviewee";

        // Fetch question details
        Long questionID = upcomingInterview.isInterviewerRole() ? upcomingInterview.getQuestionId() : upcomingInterview.getQuestionIDForPeer();

        Question question = questionRepository.findById(questionID)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionID));

        // Build GetRoomPayloadDTO
        GetRoomPayloadDTO payload = new GetRoomPayloadDTO();


        // Set roomDetails
        GetRoomPayloadDTO.RoomDetails roomDetails = new GetRoomPayloadDTO.RoomDetails();
        roomDetails.setRoomIDHash(upcomingInterview.getRoomIDHash());
        roomDetails.setInterviewID(interviewID);

        InterviewSlot interviewSlot = interviewSlotRepository.findBySlotId(upcomingInterview.getSlotId()).orElseThrow(
                ()->new ResourceNotFoundException("Slot ID: "+ upcomingInterview.getSlotId()+" does not exist")
        ) ;
        roomDetails.setInterviewStartTime(getSlotTime(interviewSlot));

        //TODO: Now hard coded 1hr, probably fetch details of how Long interview lasts
        roomDetails.setInterviewEndTime(getSlotTime(interviewSlot).plusHours(1));


        payload.setRoomDetails(roomDetails);



        // Set userDetails
        GetRoomPayloadDTO.UserDetails userDetails = new GetRoomPayloadDTO.UserDetails();
        userDetails.setUserID(user.getId());
        userDetails.setUserName(user.getUsername());
        userDetails.setFullName(user.getFirstName() + " " + user.getLastName());
        userDetails.setInterviewRole(userRole);
        payload.setUserDetails(userDetails);


        // Set peerInfo


        GetRoomPayloadDTO.PeerInfo peerInfo = new GetRoomPayloadDTO.PeerInfo();
        peerInfo.setPeerRole(peerRole);

        QuestionResponseDto questionResponseDto = this.questionService.getQuestionById(questionID);
        peerInfo.setQuestion(questionResponseDto);
        payload.setPeerInfo(peerInfo);

        return payload;
    }
}
