package com.oocl.ita.web.service;

import com.oocl.ita.web.common.utils.TimeUtils;
import com.oocl.ita.web.domain.bo.ReleaseTicketTaskBody;
import com.oocl.ita.web.domain.po.ConcertClass;
import com.oocl.ita.web.domain.po.ConcertScheduleClass;
import com.oocl.ita.web.domain.po.TicketRelease;
import com.oocl.ita.web.repository.ConcertClassRepository;
import com.oocl.ita.web.repository.ConcertScheduleClassRepository;
import com.oocl.ita.web.repository.TicketReleaseRepository;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Service
public class ReleaseTicketTaskService {

    private ConcertClassRepository concertClassRepository;

    private ConcertScheduleClassRepository concertScheduleClassRepository;

    private TicketReleaseRepository ticketReleaseRepository;

    private final TaskScheduler taskScheduler = new ConcurrentTaskScheduler();

    // 用于保存定时任务的返回值，以便取消定时任务
    private Map<Integer, ScheduledFuture<?>> scheduleIdMapScheduledFuture = new HashMap<>();

    public ReleaseTicketTaskService(ConcertClassRepository concertClassRepository,
                                    ConcertScheduleClassRepository concertScheduleClassRepository,
                                    TicketReleaseRepository ticketReleaseRepository) {
        this.concertClassRepository = concertClassRepository;
        this.concertScheduleClassRepository = concertScheduleClassRepository;
        this.ticketReleaseRepository = ticketReleaseRepository;
    }

    public boolean scheduleReleaseTicketTask(Integer concertId, Integer concertScheduleId, ReleaseTicketTaskBody releaseTicketTaskBody) {
        if (ticketReleaseRepository.findByConcertScheduleId(concertScheduleId) != null) {
            return false;
        }
        TicketRelease ticketRelease = new TicketRelease();
        ticketRelease.setConcertScheduleId(concertScheduleId);
        ticketRelease.setFrequency(releaseTicketTaskBody.getRepeatCount());
        ticketRelease.setStartTime(releaseTicketTaskBody.getStartTime());
        ticketRelease.setEndTime(releaseTicketTaskBody.getEndTime());
        ticketReleaseRepository.save(ticketRelease);

        int repeatCount = releaseTicketTaskBody.getRepeatCount();
        String cronExpression = "0 0 %d * * ?".formatted(releaseTicketTaskBody.getHour());

        Date startTime = TimeUtils.stringToDate(releaseTicketTaskBody.getStartTime(), "yyyy-MM-dd");
        Date endTime = TimeUtils.stringToDate(releaseTicketTaskBody.getEndTime(), "yyyy-MM-dd");

        List<ConcertClass> concertClasses = concertClassRepository.findByConcertId(concertId);
        final Map<Integer, List<Integer>> concertClassMapQuantities =
                generateConcertClassQuantityMapping(concertClasses, repeatCount);

        LocalDate startTimeLocalDate = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endTimeLocalDate = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long apartDay = ChronoUnit.DAYS.between(endTimeLocalDate, startTimeLocalDate) / repeatCount;

        Runnable releaseTicketTask = new Runnable() {
            private int count = 0;
            LocalDate lastExecutionDate = startTimeLocalDate.minusDays(apartDay);
            @Override
            public void run() {
                LocalDate currentDate = LocalDate.now();
                long between = ChronoUnit.DAYS.between(currentDate, lastExecutionDate);
                if (between >= apartDay && count < repeatCount) {
                    List<ConcertClass> classes = concertClassRepository.findByConcertId(concertId);
                    classes.forEach(concertClass -> {
                        Integer quantity = concertClassMapQuantities.get(concertClass.getId()).get(count);
                        if (quantity == null) {
                            cancelReleaseTicketTask(concertScheduleId);
                            return;
                        }
                        ConcertScheduleClass concertScheduleClass =
                                concertScheduleClassRepository.findByConcertScheduleIdAndConcertClassId(concertScheduleId, concertClass.getId());
                        concertScheduleClass.setAvailableSeats(concertScheduleClass.getAvailableSeats() + quantity);
                        concertScheduleClassRepository.save(concertScheduleClass);
                    });
                    count++;
                    lastExecutionDate = currentDate;
                }
                if (count >= repeatCount || currentDate.isAfter(endTimeLocalDate)) {
                    cancelReleaseTicketTask(concertScheduleId);
                }
            }
        };

        ScheduledFuture<?> schedule = taskScheduler.schedule(releaseTicketTask, new CronTrigger(cronExpression));
        scheduleIdMapScheduledFuture.put(concertScheduleId, schedule);
        return true;
    }

    private static Map<Integer, List<Integer>> generateConcertClassQuantityMapping(List<ConcertClass> concertClasses, int repeatCount) {
        final Map<Integer, List<Integer>> quantityMap = new HashMap<>();

        for (ConcertClass concertClass : concertClasses) {
            List<Integer> quantities = new ArrayList<>(repeatCount);
            int capacity = concertClass.getCapacity();
            for (int i = 0; i < repeatCount - 1; i++) {
                quantities.add(capacity / repeatCount);
            }
            quantities.add(capacity - (capacity / repeatCount) * (repeatCount - 1));
            quantityMap.put(concertClass.getId(), quantities);
        }
        return quantityMap;
    }

    private void cancelReleaseTicketTask(Integer concertScheduleId) {
        ScheduledFuture<?> scheduledFuture = scheduleIdMapScheduledFuture.remove(concertScheduleId);
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
        }
    }
}
