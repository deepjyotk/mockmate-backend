package com.mockmate.auth_service.service.interview_timings;

import com.mockmate.auth_service.entities.interview.InterviewSlot;
import com.mockmate.auth_service.entities.interview.InterviewTypeTime;

import com.mockmate.auth_service.repository.interview.InterviewSlotRepository;
import com.mockmate.auth_service.repository.interview.InterviewTypeTimeRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewScheduleServiceImpl implements InterviewScheduleService {

    private final InterviewSlotRepository interviewSlotRepository;
    private final InterviewTypeTimeRepository interviewTypeTimeRepository;

    public InterviewScheduleServiceImpl(InterviewSlotRepository interviewSlotRepository,
                                        InterviewTypeTimeRepository interviewTypeTimeRepository) {
        this.interviewSlotRepository = interviewSlotRepository;
        this.interviewTypeTimeRepository = interviewTypeTimeRepository;
    }

    @Override
    public void createAndInsertSchedule() {
        // Fetch all interview types
        List<InterviewTypeTime> interviewTypeTimes = interviewTypeTimeRepository.findAll();

        // Generate dates for this and next weekends
        LocalDate today = LocalDate.now();
        LocalDate thisSaturday = getNextDayOfWeek(today, DayOfWeek.SATURDAY);
        LocalDate thisSunday = thisSaturday.plusDays(1);
        LocalDate nextSaturday = thisSaturday.plusDays(7);
        LocalDate nextSunday = thisSunday.plusDays(7);

        List<InterviewSlot> interviewSlots = new ArrayList<>();

        // Create slots for each interview type time
        for (InterviewTypeTime interviewTypeTime : interviewTypeTimes) {
            interviewSlots.add(new InterviewSlot(interviewTypeTime, thisSaturday));
            interviewSlots.add(new InterviewSlot(interviewTypeTime, thisSunday));
            interviewSlots.add(new InterviewSlot(interviewTypeTime, nextSaturday));
            interviewSlots.add(new InterviewSlot(interviewTypeTime, nextSunday));
        }

        // Save all slots to the database
        interviewSlotRepository.saveAll(interviewSlots);
    }

    private LocalDate getNextDayOfWeek(LocalDate currentDate, DayOfWeek dayOfWeek) {
        int daysUntilNext = dayOfWeek.getValue() - currentDate.getDayOfWeek().getValue();
        if (daysUntilNext < 0) {
            daysUntilNext += 7;
        }
        return currentDate.plusDays(daysUntilNext);
    }
}
