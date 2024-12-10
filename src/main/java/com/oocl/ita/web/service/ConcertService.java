package com.oocl.ita.web.service;

import com.oocl.ita.web.core.exception.ConcertInProgressException;
import com.oocl.ita.web.core.exception.EntityNotExistException;
import com.oocl.ita.web.domain.bo.ConcertClassBody;
import com.oocl.ita.web.domain.bo.ConcertClassUpdateBody;
import com.oocl.ita.web.domain.po.Concert;
import com.oocl.ita.web.domain.po.ConcertClass;
import com.oocl.ita.web.domain.po.ConcertSchedule;
import com.oocl.ita.web.domain.po.Venue;
import com.oocl.ita.web.domain.vo.ConcertClassVo;
import com.oocl.ita.web.domain.vo.ConcertSessionVo;
import com.oocl.ita.web.repository.ConcertClassRepository;
import com.oocl.ita.web.repository.ConcertRepository;
import com.oocl.ita.web.repository.ConcertScheduleRepository;
import com.oocl.ita.web.repository.VenueRepository;
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

    public ConcertService(ConcertClassRepository concertClassRepository, ConcertScheduleRepository concertScheduleRepository, ConcertRepository concertRepository, VenueRepository venueRepository) {
        this.concertClassRepository = concertClassRepository;
        this.concertScheduleRepository = concertScheduleRepository;
        this.concertRepository = concertRepository;
        this.venueRepository = venueRepository;
    }

    public ConcertClassVo addConcertClass(Integer concertId,ConcertClassBody concertClassBody) {
        List<ConcertSchedule> concertSchedules = concertScheduleRepository.findByConcertIdOrderByStartTimeAsc(concertId);
        if (concertSchedules == null || concertSchedules.isEmpty()) {
            throw new EntityNotExistException("ConcertSchedule");
        }
        Date startDate = stringToDate(concertSchedules.get(0).getStartTime());
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
        if (concertClassBody.getClassName() != null) {
            concertClass.setClassName(concertClassBody.getClassName());
        }
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
        return concertClassRepository.findByConcertId(concertId).stream()
                .map(this::buildConcertClassVo)
                .toList();
    }

    public ConcertClassVo updateConcertClass(Integer concertId, Integer classId,
                                             ConcertClassUpdateBody concertClassBody) {
        ConcertClass concertClass = concertClassRepository.findById(classId).orElseThrow(() -> new EntityNotExistException("ConcertClass"));
        ConcertClass updatedConcertClass = concertClassRepository.save(buildUpdatedConcertClass(concertClass, concertClassBody));
        return buildConcertClassVo(updatedConcertClass);
    }

    public ConcertSessionVo getConcertSession(Integer concertScheduleId) {
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(concertScheduleId).orElseThrow(() -> new EntityNotExistException("ConcertSchedule"));

        Concert concert = concertRepository.getById(concertSchedule.getConcertId());

        Venue venue = venueRepository.findById(concert.getVenueId()).orElseThrow(() -> new EntityNotExistException("Venue"));;

        if (concertSchedule == null) {
            throw new EntityNotExistException("ConcertSchedule");
        }

        return getConcertSessionVo(concertSchedule, concert, venue);
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

            return getConcertSessionVo(concertSchedule, concert, venue);
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

        return concertSessionVo;
    }


}
