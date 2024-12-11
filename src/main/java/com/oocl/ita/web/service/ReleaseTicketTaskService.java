package com.oocl.ita.web.service;

import com.oocl.ita.web.common.utils.TimeUtils;
import com.oocl.ita.web.domain.bo.ReleaseTicketTaskBody;
import com.oocl.ita.web.domain.po.*;
import com.oocl.ita.web.domain.vo.ConcertClassVo;
import com.oocl.ita.web.domain.vo.ConcertScheduleTicketReleaseVo;
import com.oocl.ita.web.domain.vo.ConcertScheduleVo;
import com.oocl.ita.web.domain.vo.TicketReleaseVo;
import com.oocl.ita.web.repository.*;
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

    private ConcertRepository concertRepository;

    private ConcertScheduleRepository concertScheduleRepository;

    private final TaskScheduler taskScheduler = new ConcurrentTaskScheduler();

    // 用于保存定时任务的返回值，以便取消定时任务
    private Map<Integer, ScheduledFuture<?>> scheduleIdMapScheduledFuture = new HashMap<>();

    public ReleaseTicketTaskService(ConcertClassRepository concertClassRepository,
                                    ConcertScheduleClassRepository concertScheduleClassRepository,
                                    TicketReleaseRepository ticketReleaseRepository,
                                    ConcertRepository concertRepository,
                                    ConcertScheduleRepository concertScheduleRepository) {
        this.concertClassRepository = concertClassRepository;
        this.concertScheduleClassRepository = concertScheduleClassRepository;
        this.ticketReleaseRepository = ticketReleaseRepository;
        this.concertRepository = concertRepository;
        this.concertScheduleRepository = concertScheduleRepository;
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
        ticketRelease.setHour(releaseTicketTaskBody.getHour());
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
                    TicketRelease saveTicketRelease = ticketReleaseRepository.findByConcertScheduleId(concertScheduleId);
                    saveTicketRelease.setNextPresellTime(count == repeatCount ? currentDate.toString() : currentDate.plusDays(apartDay).toString());
                    ticketReleaseRepository.save(saveTicketRelease);
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

    public List<ConcertScheduleTicketReleaseVo> getConcertReleaseTicketTask() {
        // 获取concerts
        List<Concert> concerts = concertRepository.findAll();
        if (concerts.isEmpty()) {
            return Collections.emptyList();
        }

        List<ConcertScheduleTicketReleaseVo> concertScheduleTicketReleaseVos = new ArrayList<>(concerts.size());

        // 获取concert下最小startTime的schedule
        concerts.forEach(concert -> {
            ConcertScheduleTicketReleaseVo releaseVo = new ConcertScheduleTicketReleaseVo();
            releaseVo.setConcertId(concert.getId());
            releaseVo.setConcertName(concert.getName());
            List<ConcertSchedule> concertSchedules = concertScheduleRepository.findByConcertIdOrderByStartTimeAsc(concert.getId());
            if (concertSchedules.isEmpty()) {
                return;
            }
            // 获取schedule下的所有concertClass
            ConcertSchedule concertSchedule = concertSchedules.get(0);
            ConcertScheduleVo concertScheduleVo = new ConcertScheduleVo();
            concertScheduleVo.setScheduleId(concertSchedule.getId());
            concertScheduleVo.setStrartTime(concertSchedule.getStartTime());
            List<ConcertClassVo> concertClassVos = new ArrayList<>();

            List<ConcertScheduleClass> concertScheduleClasses = concertScheduleClassRepository.findByConcertScheduleId(concertSchedule.getId());
            if (concertScheduleClasses.isEmpty()) {
                return;
            }
            for (ConcertScheduleClass concertScheduleClass : concertScheduleClasses) {
                concertClassRepository.findById(concertScheduleClass.getConcertClassId()).ifPresent(concertClass -> {
                    ConcertClassVo concertClassVo = new ConcertClassVo();
                    concertClassVo.setClassName(concertClass.getClassName());
                    concertClassVo.setId(concertClass.getId());
                    concertClassVo.setPrice(concertClass.getPriceInUsd());
                    concertClassVo.setCapacity(concertClass.getCapacity());
                    concertClassVo.setAvailableSeats(concertScheduleClass.getAvailableSeats());
                    concertClassVos.add(concertClassVo);
                });
            }

            concertScheduleVo.setConcertClasses(concertClassVos);
            releaseVo.setConcertSchedule(concertScheduleVo);
            // 获取schedule的ticketRelease
            TicketRelease ticketRelease = ticketReleaseRepository.findByConcertScheduleId(concertSchedule.getId());
            if (ticketRelease != null) {
                releaseVo.setTicketRelease(TicketReleaseVo.toVo(ticketRelease));
            }
            concertScheduleTicketReleaseVos.add(releaseVo);
        });
        return concertScheduleTicketReleaseVos;
    }
}
