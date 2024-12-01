package com.mockmate.auth_service.service.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class SchedulerService {

//    private final RoomServiceImpl roomService;


    public SchedulerService() {
//        this.roomService = roomService;
    }

    /**
     * Schedules a task to check pairing after 1 minute.
     *
     * @param interviewId The ID of the interview to check.
     */
    public void schedulePairingCheck(Long interviewId) {
        // Schedule the pairing check after 1 minute
        // Using Spring's @Scheduled is not ideal for dynamic scheduling, so use a ScheduledExecutorService
        // Alternatively, use Spring's TaskScheduler

//        TaskScheduler scheduler = taskScheduler();
//        scheduler.schedule(() -> roomService.handlePairingTimeout(interviewId),
//                new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)));
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }
}