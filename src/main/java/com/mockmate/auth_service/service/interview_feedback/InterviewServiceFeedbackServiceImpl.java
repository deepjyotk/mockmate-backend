package com.mockmate.auth_service.service.interview_feedback;

import com.mockmate.auth_service.dto.feedback.FeedbackRequestDTO;
import com.mockmate.auth_service.entities.interview.InterviewFeedback;
import com.mockmate.auth_service.entities.interview.InterviewType;
import com.mockmate.auth_service.entities.interview.PastInterviews;
import com.mockmate.auth_service.entities.interview.UpcomingInterviews;
import com.mockmate.auth_service.exception.custom.ResourceNotFoundException;
import com.mockmate.auth_service.repository.interview.*;
import jakarta.transaction.Transactional;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class InterviewServiceFeedbackServiceImpl implements  InterviewFeedbackService{
    private final UpcomingInterviewRepository upcomingInterviewsRepository;
    private final PastInterviewRepository pastInterviewsRepository;
    private final InterviewFeedbackRepository interviewFeedbackRepository;
    private final InterviewSlotRepository interviewSlotRepository ;

    private final UpcomingInterviewUserPreferenceRepository upcomingInterviewUserPreferenceRepository ;

    public InterviewServiceFeedbackServiceImpl(UpcomingInterviewRepository upcomingInterviewsRepository,
                                               PastInterviewRepository pastInterviewsRepository,
                                    InterviewFeedbackRepository interviewFeedbackRepository,
                                               InterviewSlotRepository interviewSlotRepository,
                                               UpcomingInterviewUserPreferenceRepository upcomingInterviewUserPreferenceRepository) {
        this.upcomingInterviewsRepository = upcomingInterviewsRepository;
        this.pastInterviewsRepository = pastInterviewsRepository;
        this.interviewFeedbackRepository = interviewFeedbackRepository;
        this.interviewSlotRepository = interviewSlotRepository ;
        this.upcomingInterviewUserPreferenceRepository = upcomingInterviewUserPreferenceRepository;
    }

    @Transactional
    public void handleFeedback(FeedbackRequestDTO feedbackRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // userId from token


        String roomHash = feedbackRequest.getRoomHash() ;
        UpcomingInterviews interview =  upcomingInterviewsRepository.findByUserIdAndRoomIDHash(Long.parseLong(userId), roomHash);
        var slot = interviewSlotRepository.findBySlotId(interview.getSlotId()).orElseThrow(()->new ResourceNotFoundException("Slot ID not found"));


        Long peerInterviewID = interview.getPeerInterview().getUpcomingInterviewId();

        var pastInterviewForMeAndPeer = getPastInterviewForMeAndPeer(peerInterviewID, slot.getInterviewTypeTime().getInterviewType());


        InterviewFeedback feedback = new InterviewFeedback();
        feedback.setPastInterview(pastInterviewForMeAndPeer.getSecond());
        feedback.setCommunicationSkillsRating(feedbackRequest.getPeerFeedback().getCommunicationSkillsRating());
        feedback.setTechnicalSkillsRating(feedbackRequest.getPeerFeedback().getTechnicalSkillsRating());
        feedback.setDidWellText(feedbackRequest.getPeerFeedback().getDidWellText());
        feedback.setThingsToImproveText(feedbackRequest.getPeerFeedback().getThingsToImproveText());
        feedback.setNextRoundSelection(feedbackRequest.getPeerFeedback().getNextRoundSelection());
        feedback.setGoodMatchForPeerRating(feedbackRequest.getPeerFeedback().getGoodMatchForPeerRating());

        interviewFeedbackRepository.save(feedback);
    }


    // create PastInterview from UpcomingInterview
    private Pair<PastInterviews, PastInterviews> getPastInterviewForMeAndPeer(Long interviewID, InterviewType interviewType) {
        // Check if past interview already exists
        Optional<PastInterviews> existingPast = pastInterviewsRepository.findById(interviewID);
        if (existingPast.isPresent()) {
            return Pair.of(existingPast.get() , existingPast.get().getPeerInterview() );
        }

        // If not present, fetch upcoming interview
        UpcomingInterviews upcoming = upcomingInterviewsRepository.findById(interviewID)
                .orElseThrow(() -> new IllegalArgumentException("Upcoming interview not found: " + interviewID));

        UpcomingInterviews peerInterview = upcoming.getPeerInterview() ;

        // Create past interview for me.
        PastInterviews pastInterview = new PastInterviews();
        pastInterview.setPastInterviewId(upcoming.getUpcomingInterviewId());
        pastInterview.setUserId(upcoming.getUserId());
        pastInterview.setStatus(upcoming.getStatus());

        pastInterview.setQuestionIDForPeer(upcoming.getQuestionIDForPeer());
        pastInterview.setQuestionId(upcoming.getQuestionId());
        pastInterview.setInterviewType(interviewType);

        OffsetDateTime now = OffsetDateTime.now() ;
        pastInterview.setDateAndTime(now );



        PastInterviews peerPastInterview = new PastInterviews( );
        peerPastInterview.setPastInterviewId(peerInterview.getUpcomingInterviewId());
        peerPastInterview.setUserId(peerInterview.getUserId());
        peerPastInterview.setStatus(peerInterview.getStatus());

        peerPastInterview.setQuestionIDForPeer(peerInterview.getQuestionIDForPeer());
        peerPastInterview.setQuestionId(peerInterview.getQuestionId());
        peerPastInterview.setInterviewType(interviewType);
        peerPastInterview.setDateAndTime(now);

        pastInterview.setPeerInterview(null);
        peerPastInterview.setPeerInterview(null);



        pastInterview = pastInterviewsRepository.save(pastInterview);
        peerPastInterview = pastInterviewsRepository.save(peerPastInterview);


        pastInterview.setPeerInterview(peerPastInterview);
        peerPastInterview.setPeerInterview(pastInterview);

        pastInterview = pastInterviewsRepository.save(pastInterview);
        peerPastInterview = pastInterviewsRepository.save(peerPastInterview);


        upcomingInterviewUserPreferenceRepository.delete(upcoming.getUpcomingInterviewUserPreference());
        upcomingInterviewUserPreferenceRepository.delete(peerInterview.getUpcomingInterviewUserPreference());
        upcoming.setUpcomingInterviewUserPreference(null);
        peerInterview.setUpcomingInterviewUserPreference(null);

        upcoming.setPeerInterview(null);
        peerInterview.setPeerInterview(null);



        upcoming = upcomingInterviewsRepository.save(upcoming) ;
        peerInterview = upcomingInterviewsRepository.save(peerInterview) ;


        upcomingInterviewsRepository.delete(upcoming);
        upcomingInterviewsRepository.delete(peerInterview);

        return Pair.of(pastInterview, peerPastInterview);
    }

}
