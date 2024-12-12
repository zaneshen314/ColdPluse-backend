package com.oocl.ita.web.service;

import com.oocl.ita.web.core.exception.ConcertInProgressException;
import com.oocl.ita.web.core.exception.EntityNotExistException;
import com.oocl.ita.web.domain.bo.Concert.ConcertClassBody;
import com.oocl.ita.web.domain.bo.Concert.ConcertClassUpdateBody;
import com.oocl.ita.web.domain.bo.Concert.ConcertScheduleRegBody;
import com.oocl.ita.web.domain.po.Concert.*;
import com.oocl.ita.web.domain.po.Ticket.TicketRelease;
import com.oocl.ita.web.domain.vo.Concert.ConcertClassVo;
import com.oocl.ita.web.domain.vo.Concert.ConcertSessionVo;
import com.oocl.ita.web.domain.vo.Concert.ConcertVo;
import com.oocl.ita.web.repository.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.oocl.ita.web.common.utils.TimeUtils.getCurrentTime;
import static com.oocl.ita.web.common.utils.TimeUtils.stringToDate;

@Service
public class ConcertService {

    public static final String Comma = ", ";
    private ConcertRepository concertRepository;

    private ConcertClassRepository concertClassRepository;

    private ConcertScheduleRepository concertScheduleRepository;

    private VenueRepository venueRepository;

    private ConcertScheduleClassRepository concertScheduleClassRepository;

    private TicketReleaseRepository ticketReleaseRepository;

    public ConcertService(ConcertClassRepository concertClassRepository,
                          ConcertScheduleRepository concertScheduleRepository,
                          ConcertRepository concertRepository,
                          VenueRepository venueRepository,
                          ConcertScheduleClassRepository concertScheduleClassRepository,
                          TicketReleaseRepository ticketReleaseRepository) {
        this.concertClassRepository = concertClassRepository;
        this.concertScheduleRepository = concertScheduleRepository;
        this.concertRepository = concertRepository;
        this.venueRepository = venueRepository;
        this.concertScheduleClassRepository = concertScheduleClassRepository;
        this.ticketReleaseRepository = ticketReleaseRepository;
    }

    public ConcertClassVo addConcertClass(Integer concertId, ConcertClassBody concertClassBody) {
        List<ConcertSchedule> concertSchedules = concertScheduleRepository.findByConcertIdOrderByStartTimeAsc(concertId);
        if (concertSchedules == null || concertSchedules.isEmpty()) {
            throw new EntityNotExistException("ConcertSchedule");
        }
        Date startDate = stringToDate(concertSchedules.get(0).getStartTime(), "yyyy-MM-dd");
        if (startDate.before(getCurrentTime())) {
            throw new ConcertInProgressException();
        }
        ConcertClass concertClass = initCreatedConcertClass(concertId, concertClassBody);
        ConcertClass saveConcertClass = concertClassRepository.save(concertClass);
        return buildConcertClassVo(saveConcertClass);
    }

    private ConcertClassVo buildConcertClassVo(ConcertClass concertClass) {
        ConcertClassVo concertClassVo = new ConcertClassVo();
        concertClassVo.setId(concertClass.getId());
        concertClassVo.setClassName(concertClass.getClassName());
        concertClassVo.setCapacity(concertClass.getCapacity());
        concertClassVo.setAvailableSeats(concertClass.getAvailableSeats());
        concertClassVo.setPrice(concertClass.getPriceInLocalCurr());
        concertClassVo.setConcertId(concertClass.getConcertId());
        return concertClassVo;
    }

    private ConcertClass initCreatedConcertClass(Integer concertId, ConcertClassBody concertClassBody) {
        ConcertClass concertClass = new ConcertClass();
        concertClass.setClassName(concertClassBody.getClassName());
        concertClass.setCapacity(concertClassBody.getCapacity());
        concertClass.setPriceInUsd(generatePriceInUsd(concertClassBody.getCurrency(), concertClassBody.getPrice()));
        concertClass.setPriceInLocalCurr(concertClassBody.getPrice());
        concertClass.setAvailableSeats(0);
        concertClass.setCurrency(concertClass.getCurrency());
        concertClass.setConcertId(concertId);
        return concertClass;
    }

    private ConcertClass buildUpdatedConcertClass(ConcertClass concertClass, ConcertClassUpdateBody concertClassBody) {
        if (concertClassBody.getCurrency() != null && concertClassBody.getPrice() != null) {
            concertClass.setPriceInUsd(generatePriceInUsd(concertClassBody.getCurrency(), concertClassBody.getPrice()));
            concertClass.setPriceInLocalCurr(concertClassBody.getPrice());
            concertClass.setCurrency(concertClass.getCurrency());
        }
        return concertClass;
    }

    private double generatePriceInUsd(String currency, double price) {
        return switch (currency) {
            case "USD" -> price;
            case "CNY" -> price / 7d;
            case "HKD" -> price / 7.8d;
            default -> price / 6.8d;
        };
    }

    public List<ConcertClassVo> listConcertClasses(Integer concertId) {
        List<ConcertClass> concertClasses = concertClassRepository.findByConcertId(concertId);
        if (concertClasses == null || concertClasses.isEmpty()) {
            throw new EntityNotExistException("ConcertClass");
        }
        return concertClasses.stream().map(this::buildConcertClassVo).toList();
    }

    public ConcertClassVo updateConcertClass(Integer concertId, Integer scheduleId, Integer classId,
                                             ConcertClassUpdateBody concertClassBody) {
        ConcertClass concertClass = concertClassRepository.findById(classId).orElseThrow(() -> new EntityNotExistException("ConcertClass"));
        ConcertClass updatedConcertClass = concertClassRepository.save(buildUpdatedConcertClass(concertClass, concertClassBody));
        return buildConcertClassVo(updatedConcertClass);
    }

    public ConcertSessionVo getConcertSession(Integer concertScheduleId) {
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(concertScheduleId).orElseThrow(() -> new EntityNotExistException("ConcertSchedule"));

        Concert concert = concertRepository.getById(concertSchedule.getConcertId());

        Venue venue = venueRepository.findById(concert.getVenueId()).orElseThrow(() -> new EntityNotExistException("Venue"));

        TicketRelease ticketRelease = ticketReleaseRepository.findByConcertScheduleId(concertScheduleId);

        if (concertSchedule == null) {
            throw new EntityNotExistException("ConcertSchedule");
        }

        ConcertSessionVo concertSessionVo = getConcertSessionVo(concertSchedule, concert, venue);
        if (ticketRelease != null) {
            concertSessionVo.setNextPresellTime(ticketRelease.getNextPresellTime());
        }
        return concertSessionVo;
    }

    public List<ConcertSessionVo> getAllConcertSessions() {
        // Fetch all concert schedules
        List<ConcertSchedule> concertSchedules = concertScheduleRepository.findAll();

        if (concertSchedules.isEmpty()) {
            throw new EntityNotExistException("No concert schedules found.");
        }

        // Map each concert schedule to its respective ConcertSessionVo
        return concertSchedules.stream().map(concertSchedule -> {
            Concert concert = concertRepository.getById(concertSchedule.getConcertId());

            Venue venue = venueRepository.findById(concert.getVenueId())
                    .orElseThrow(() -> new EntityNotExistException("Venue"));

            TicketRelease ticketRelease = ticketReleaseRepository.findByConcertScheduleId(concertSchedule.getId());
            ConcertSessionVo concertSessionVo = getConcertSessionVo(concertSchedule, concert, venue);
            if (ticketRelease != null) {
                concertSessionVo.setNextPresellTime(ticketRelease.getNextPresellTime());
            }
            return concertSessionVo;
        }).collect(Collectors.toList());
    }

    public List<ConcertSessionVo> getConcertSessionsByConcertId(Integer concertId) {
        List<ConcertSchedule> concertSchedules = concertScheduleRepository.findByConcertIdOrderByStartTimeAsc(concertId);

        if (concertSchedules.isEmpty()) {
            throw new EntityNotExistException("No concert schedules found.");
        }

        return concertSchedules.stream().map(concertSchedule -> {
            Concert concert = concertRepository.getById(concertSchedule.getConcertId());

            Venue venue = venueRepository.findById(concert.getVenueId())
                    .orElseThrow(() -> new EntityNotExistException("Venue"));

            TicketRelease ticketRelease = ticketReleaseRepository.findByConcertScheduleId(concertSchedule.getId());
            ConcertSessionVo concertSessionVo = getConcertSessionVo(concertSchedule, concert, venue);
            if (ticketRelease != null) {
                concertSessionVo.setNextPresellTime(ticketRelease.getNextPresellTime());
            }
            return concertSessionVo;
        }).collect(Collectors.toList());
    }

    private ConcertSessionVo getConcertSessionVo(ConcertSchedule concertSchedule, Concert concert, Venue venue) {
        ConcertSessionVo concertSessionVo = new ConcertSessionVo();
        concertSessionVo.setConcertId(concertSchedule.getConcertId());
        concertSessionVo.setScheduleId(concertSchedule.getId());
        concertSessionVo.setName(concert.getName());
        concertSessionVo.setMaxPrice(concertClassRepository.getMaxPriceByConcertId(concertSchedule.getConcertId()));
        concertSessionVo.setMinPrice(concertClassRepository.getMinPriceByConcertId(concertSchedule.getConcertId()));
        concertSessionVo.setVenue(venue.getName() + Comma + venue.getLocation() + Comma + venue.getState());
        concertSessionVo.setStart_time(concertSchedule.getStartTime());
        concertSessionVo.setDuration(concertSchedule.getDuration());
        concertSessionVo.setImgUrl(concert.getImgUrl());
        concertSessionVo.setSaleStartTime(concertSchedule.getSaleStartTime());
        return concertSessionVo;
    }

    public ConcertVo getConcertByConcertId(Integer concertId){
        Concert concert = concertRepository.getById(concertId);
        Venue venue = venueRepository.findById(concert.getVenueId())
                .orElseThrow(() -> new EntityNotExistException("Venue"));
        ConcertVo concertVo = new ConcertVo();
        concertVo.setId(concertId);
        concertVo.setVenue(venue);
        concertVo.setName(concert.getName());
        return concertVo;
    }


    public ConcertSchedule addConcertSchedule(ConcertScheduleRegBody concertScheduleRegBody) {
        ConcertSchedule concertSchedule = concertScheduleRepository.save(new ConcertSchedule(concertScheduleRegBody));
        concertScheduleClassRepository.saveAll(concertClassRepository.findByConcertId(concertScheduleRegBody.getConcertId())
                .stream().map(concertClass -> new ConcertScheduleClass(concertSchedule.getId(), concertClass.getId())).toList());
        return concertSchedule;
    }

    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    public List<ConcertScheduleClass> listConcertScheduleClasses(Integer scheduleId) {
        return concertScheduleClassRepository.findByConcertScheduleId(scheduleId).stream().toList();
    }
}
