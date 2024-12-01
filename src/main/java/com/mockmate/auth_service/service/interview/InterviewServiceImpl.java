package com.mockmate.auth_service.service.interview;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockmate.auth_service.dto.interview.*;
import com.mockmate.auth_service.entities.interview.*;
import com.mockmate.auth_service.enums.InterviewStatusEnum;
import com.mockmate.auth_service.exception.custom.NotAllowedException;
import com.mockmate.auth_service.exception.custom.ResourceNotFoundException;
import com.mockmate.auth_service.exception.custom.SlotAlreadyBookedException;
import com.mockmate.auth_service.repository.interview.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private  InterviewLevelRepository interviewLevelRepository;

    @Autowired
    private  InterviewTypeRepository interviewTypeRepository;

    @Autowired
    private  InterviewSlotRepository interviewSlotRepository;
    @Autowired
    private UpcomingInterviewRepository upcomingInterviewRepository;

    @Autowired
    private UpcomingInterviewUserPreferenceRepository upcomingInterviewUserPreferenceRepository;

    @Autowired
    private InterviewStatusRepository interviewStatusRepository ;

    @Autowired
    private PastInterviewRepository pastInterviewRepository ;


    private final ObjectMapper objectMapper = new ObjectMapper();



    @Transactional
    public InterviewScheduleResponseDto scheduleInterview(InterviewScheduleDto interviewScheduleDto) {
            // Get user ID from SecurityContextHolder
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdString = authentication.getName() ;
            Long slotId = Long.parseLong(interviewScheduleDto.getInterviewSlotId());
            Long userId = Long.parseLong(userIdString);

            // Create UpcomingInterview entity
            UpcomingInterviews upcomingInterview = new UpcomingInterviews();
            upcomingInterview.setSlotId(Long.parseLong(interviewScheduleDto.getInterviewSlotId()));
            upcomingInterview.setUserId(Long.parseLong(userIdString) );

            InterviewStatus interviewStatus = interviewStatusRepository.findByStatusName("UPCOMING")
                    .orElseThrow(() -> new RuntimeException("Status 'UPCOMING' not found"));

            upcomingInterview.setStatus(interviewStatus);



            if (upcomingInterviewRepository.existsBySlotIdAndUserId(slotId, userId)) {
                throw new SlotAlreadyBookedException("The selected slot is already booked for this user.");
            }



            // Save UpcomingInterview
            UpcomingInterviews savedUpcomingInterview = upcomingInterviewRepository.save(upcomingInterview);

            // Create UpcomingInterviewUserPreference entity
            UpcomingInterviewUserPreference preference = new UpcomingInterviewUserPreference();

            preference.setUpcomingInterview(savedUpcomingInterview); // Set the relationship
            preference.setPreferenceInterviewLevelId(Long.parseLong(interviewScheduleDto.getInterviewLevelId()));
            preference.setPreferenceDescription(interviewScheduleDto.getAdditionalDescription());

            upcomingInterviewUserPreferenceRepository.save(preference);

            // Prepare response DTO
            return new InterviewScheduleResponseDto(savedUpcomingInterview.getUpcomingInterviewId());


    }

    @Override
    public Optional<UpcomingInterviewsDto> updateInterview(String id, UpcomingInterviewsDto updatedFields) {
        return null;
    }

    @Override
    public boolean deleteInterview(String id) {
        return false;
    }


    private void updateInterview(UpcomingInterviewsDto upcomingInterviewsDto) {
        // Convert DTO to entity using the mapper
//        UpcomingInterviews upcomingInterviewsEntity = InterviewMapper.toEntity(upcomingInterviewsDto);
        // Update the document in MongoDB
//        interviewRepository.save(upcomingInterviewsEntity);
    }


    @Override
    public void joinWaitingRoom(Long interviewId) {
        // Step 1: Fetch the string value for WAITING_TO_JOIN
        String statusValue = InterviewStatusEnum.WAITING_TO_JOIN.getValue();

        // Step 2: Retrieve the InterviewStatus entity based on the status value
        InterviewStatus status = interviewStatusRepository.findByStatusName(statusValue)
                .orElseThrow(() -> new RuntimeException("Interview status not found: " + statusValue));

        // Step 3: Retrieve the UpcomingInterviews entity based on interviewId
        UpcomingInterviews upcomingInterview = upcomingInterviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Upcoming interview not found with ID: " + interviewId));

        // Step 4: Update the status
        upcomingInterview.setStatus(status);

        // Step 5: Save the updated entity
        upcomingInterviewRepository.save(upcomingInterview);
    }



        @Override
        @Transactional
        public ChangeInterviewStatusResponseDto changeInterviewStatus(ChangeInterviewStatusRequestDto requestDto) {
            // Fetch the upcoming interview
            UpcomingInterviews upcomingInterview = upcomingInterviewRepository.findById(requestDto.getUpcomingInterviewId())
                    .orElseThrow(() -> new ResourceNotFoundException("Upcoming Interview not found with ID: " + requestDto.getUpcomingInterviewId()));

            var authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdString = authentication.getName(); // userId from token
            Long currentUserId = Long.parseLong(userIdString) ;


            Long slotId = upcomingInterview.getSlotId() ;
            InterviewSlot slot = interviewSlotRepository.findBySlotId(slotId).orElseThrow(()-> new ResourceNotFoundException("Slot Id not found: "+slotId));
            InterviewTypeTime interviewTypeTime = slot.getInterviewTypeTime() ;
            var interviewType = interviewTypeTime.getInterviewType() ;



            if(!Objects.equals(upcomingInterview.getUserId(), currentUserId)){
                throw new NotAllowedException("User Id: "+ currentUserId + "not allowed to perform the action.");
            }

            // Fetch the new status
            InterviewStatus newStatus = interviewStatusRepository.findById(requestDto.getStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Interview Status not found with ID: " + requestDto.getStatusId()));

            // Update the status
            upcomingInterview.setStatus(newStatus);
            upcomingInterviewRepository.save(upcomingInterview);

            // Handle specific status changes
            switch (newStatus.getId().intValue()) {
                case 4: // "END"
                case 6: // "INTERRUPTED"
                case 7: // "INTERRUPTED_BY_PEER"
                    handleEndOrInterruptedStatus(upcomingInterview, interviewType, newStatus);
                    break;
                case 5: // "DID_NOT_JOIN"
                case 8: // "UNMATCHED"
                    handleDidNotJoinOrUnmatchedStatus(upcomingInterview);
                    break;
                default:
                    // For other statuses, no additional action is required
                    break;
            }

            // Prepare the response DTO
            ChangeInterviewStatusResponseDto responseDto = new ChangeInterviewStatusResponseDto(
                    upcomingInterview.getUpcomingInterviewId(),
                    newStatus.getId(),
                    newStatus.getStatusName()
            );

            return responseDto;
        }



        @Transactional
        private void handleEndOrInterruptedStatus(UpcomingInterviews upcomingInterview, InterviewType interviewType, InterviewStatus newStatus) {

            UpcomingInterviews peerUpcomingInterview = upcomingInterview.getPeerInterview() ;

            // Populate PastInterviews
            PastInterviews pastInterview = new PastInterviews();
            PastInterviews peerPastInterview = null;


            pastInterview.setUserId(upcomingInterview.getUserId());
            pastInterview.setStatus(newStatus);
            pastInterview.setPeerInterview(null);
            pastInterview.setQuestionIDForPeer(upcomingInterview.getQuestionIDForPeer());
            pastInterview.setQuestionId(upcomingInterview.getQuestionId());
            pastInterview.setDateAndTime(OffsetDateTime.now()); // Set current time
            pastInterview.setInterviewType( interviewType); // Set appropriately if available

            //SEP1: set peer interview to be  null
            upcomingInterview.setPeerInterview(null);
            upcomingInterview = upcomingInterviewRepository.save(upcomingInterview) ;




            if(peerUpcomingInterview!=null){
                peerPastInterview = new PastInterviews();
                peerPastInterview.setUserId(peerUpcomingInterview.getUserId());
                peerPastInterview.setStatus(newStatus);
                peerPastInterview.setPeerInterview(null);
                peerPastInterview.setQuestionIDForPeer(peerUpcomingInterview.getQuestionIDForPeer());
                peerPastInterview.setQuestionId(peerUpcomingInterview.getQuestionId());
                peerPastInterview.setDateAndTime(OffsetDateTime.now()); // Set current time
                peerPastInterview.setInterviewType( interviewType); // Set appropriately if available
                //STEP1: make peer interview to be null
                peerUpcomingInterview.setPeerInterview(null);
                peerUpcomingInterview = upcomingInterviewRepository.save(peerUpcomingInterview) ;

                upcomingInterviewUserPreferenceRepository.deleteByUpcomingInterview_UpcomingInterviewId(peerUpcomingInterview.getUpcomingInterviewId());
                upcomingInterviewRepository.delete(peerUpcomingInterview);

            }

            // Delete from UpcomingInterviewsUserPreference
            upcomingInterviewUserPreferenceRepository.deleteByUpcomingInterview_UpcomingInterviewId(upcomingInterview.getUpcomingInterviewId());

            // Delete the UpcomingInterview
            upcomingInterviewRepository.delete(upcomingInterview);



            pastInterview = pastInterviewRepository.save(pastInterview);

            if(peerPastInterview!=null){
                peerPastInterview = pastInterviewRepository.save(peerPastInterview);

                //update peerIds in pastInterview
                pastInterview.setPeerInterview(peerPastInterview);
                peerPastInterview.setPeerInterview(pastInterview);

                pastInterviewRepository.save(pastInterview);
                pastInterviewRepository.save(peerPastInterview);
            }
        }

        private void handleDidNotJoinOrUnmatchedStatus(UpcomingInterviews upcomingInterview) {
            // Delete from UpcomingInterviewsUserPreference
            upcomingInterviewUserPreferenceRepository.deleteByUpcomingInterview_UpcomingInterviewId(upcomingInterview.getUpcomingInterviewId());

            // Delete the UpcomingInterview
            upcomingInterviewRepository.delete(upcomingInterview);
        }



}
