package com.mockmate.auth_service.service.pair_users_service;

import com.mockmate.auth_service.entities.interview.InterviewSlot;
import com.mockmate.auth_service.entities.interview.InterviewTypeTime;
import com.mockmate.auth_service.entities.interview.UpcomingInterviewUserPreference;
import com.mockmate.auth_service.entities.interview.UpcomingInterviews;
import com.mockmate.auth_service.repository.interview.InterviewSlotRepository;
import com.mockmate.auth_service.repository.interview.InterviewTypeTimeRepository;
import com.mockmate.auth_service.repository.interview.UpcomingInterviewRepository;
import com.mockmate.auth_service.repository.interview.UpcomingInterviewUserPreferenceRepository;
import com.mockmate.auth_service.service.question.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PairUsersServiceImpl implements PairUsersService {

    @Autowired
    private InterviewSlotRepository interviewSlotRepository;

    @Autowired
    private InterviewTypeTimeRepository interviewTypeTimeRepository;

    @Autowired
    private UpcomingInterviewRepository upcomingInterviewRepository;

    @Autowired
    private UpcomingInterviewUserPreferenceRepository userPreferenceRepository;


    @Autowired
    private QuestionService questionService ;


    private  Map<Long, InterviewSlot> getNearestSlotsForSchduler( List<InterviewSlot> upcomingSlots ){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.plusDays(5);

        Map<Long, InterviewSlot> nearestSlots = new HashMap<>();
        for (InterviewSlot slot : upcomingSlots) {
            InterviewTypeTime typeTime = interviewTypeTimeRepository.findById(slot.getInterviewTypeTime().getId())
                    .orElseThrow(() -> new RuntimeException("InterviewTypeTime not found"));

            LocalDateTime slotDateTime = LocalDateTime.of(slot.getInterviewDate(),
                    LocalTime.parse(typeTime.getTime(), DateTimeFormatter.ISO_TIME));

            if (slotDateTime.isAfter(now) && slotDateTime.isBefore(targetTime.plusMinutes(1))) { // 5 minutes before
                nearestSlots.putIfAbsent(typeTime.getInterviewType().getId(), slot);
            }
        }
        return nearestSlots ;
    }

    @Transactional
    public void pairUsersForUpcomingInterviews() {


        //TODO: Ucomment this line
        List<InterviewSlot> upcomingSlots = interviewSlotRepository.findAll();

        //TODO: Comment this line
//        InterviewSlot slotIdTemp = interviewSlotRepository.findBySlotId(40017L).orElseThrow(()-> new ResourceNotFoundException("not found")) ;
//        List<InterviewSlot> upcomingSlots  = new ArrayList<>(Arrays.asList(slotIdTemp));

        // Step 2: Find the first nearest slot per interview type
        Map<Long, InterviewSlot> nearestSlots = getNearestSlotsForSchduler(upcomingSlots);


        // TODO:

        // Step 3: Iterate over each nearest slot and pair users
        for (InterviewSlot slot :upcomingSlots) {
            List<UpcomingInterviews> interviews = upcomingInterviewRepository.findBySlotId(slot.getSlotId());

            if (interviews.isEmpty()) continue;



            //Fetching only the upcomingInterviewIds which aren't paired..which means for them getPeerInterview does not exist.
            List<Long> upcomingInterviewIds = interviews.stream()
                    .filter(interview -> interview.getPeerInterview() == null)
                    .map(UpcomingInterviews::getUpcomingInterviewId)
                    .collect(Collectors.toList());
            List<UpcomingInterviewUserPreference> preferences = userPreferenceRepository.findByUpcomingInterview_UpcomingInterviewIdIn(upcomingInterviewIds);

            HashMap<Long,Long> interviewIdPairs =  this.createInterviewIdPairs(preferences) ;


            for(Map.Entry<Long,Long> interviewIdPair: interviewIdPairs.entrySet()){
                Long interviewId = interviewIdPair.getKey() ;
                UpcomingInterviews upcoming = upcomingInterviewRepository.findByUpcomingInterviewId(interviewId);
                Long userId = upcoming.getUserId() ;

                Long peerInterviewId = interviewIdPair.getValue() ;
                UpcomingInterviews peerUpcoming = upcomingInterviewRepository.findByUpcomingInterviewId(peerInterviewId);
                Long peerUserId = peerUpcoming.getUserId() ;


                Long questionID = questionService.getRandomQuestionNotOccuredInPast(interviewId,
                        slot.getInterviewTypeTime().getInterviewType().getId());

                Long peerQuestionID = questionService.getRandomQuestionNotOccuredInPast(peerInterviewId,
                        slot.getInterviewTypeTime().getInterviewType().getId());
                upcoming.setQuestionId(questionID);
                upcoming.setPeerInterview(peerUpcoming);
                upcoming.setQuestionIDForPeer(peerQuestionID);

                peerUpcoming.setQuestionId(peerQuestionID);
                peerUpcoming.setPeerInterview(upcoming);
                peerUpcoming.setQuestionIDForPeer(questionID);
            }

        }
    }




    private HashMap<Long, Long> createInterviewIdPairs(List<UpcomingInterviewUserPreference> preferences) {
        HashMap<Long, Long> interviewIdPairs = new HashMap<>();
        // Group preferences by preferenceInterviewLevelId and collect corresponding interview IDs
        Map<Long, List<Long>> preferenceToInterviewIds = preferences.stream()
                .collect(Collectors.groupingBy(
                        preference -> Long.valueOf(preference.getPreferenceInterviewLevelId()),
                        TreeMap::new,
                        Collectors.mapping(
                                preference -> preference.getUpcomingInterview().getUpcomingInterviewId(),
                                Collectors.toList()
                        )
                ));

        Long remainingId = -1L;

        // Iterate over each group to create interview ID pairs
        for (Map.Entry<Long, List<Long>> entry : preferenceToInterviewIds.entrySet()) {
            List<Long> interviewIds = new ArrayList<>(entry.getValue());

            // If there's a remaining ID from the previous group, add it to the current list
            if (remainingId != -1L) {
                interviewIds.add(remainingId);
                remainingId = -1L; // Reset remainingId after adding
            }

            // Shuffle the interview IDs to randomize pairing
            Collections.shuffle(interviewIds);

            // Pair the interview IDs
            for (int i = 0; i < interviewIds.size(); ) {
                if (i + 1 < interviewIds.size()) {
                    interviewIdPairs.put(interviewIds.get(i), interviewIds.get(i + 1));
                    i += 2; // Move to the next pair
                } else {
                    remainingId = interviewIds.get(i); // Hold the last unpaired ID
                    i++;
                }
            }
        }

        return interviewIdPairs;
    }
}
