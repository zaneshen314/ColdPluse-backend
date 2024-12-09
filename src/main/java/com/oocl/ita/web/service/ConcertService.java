package com.oocl.ita.web.service;

import com.oocl.ita.web.core.exception.ConcertInProgressException;
import com.oocl.ita.web.core.exception.EntityNotExistException;
import com.oocl.ita.web.domain.bo.ConcertClassBody;
import com.oocl.ita.web.domain.po.ConcertClass;
import com.oocl.ita.web.domain.po.ConcertSchedule;
import com.oocl.ita.web.domain.vo.ConcertClassVo;
import com.oocl.ita.web.repository.ConcertClassRepository;
import com.oocl.ita.web.repository.ConcertScheduleRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.oocl.ita.web.common.utils.TimeUtils.getCurrentTime;
import static com.oocl.ita.web.common.utils.TimeUtils.stringToDate;

@Service
public class ConcertService {

    private ConcertClassRepository concertClassRepository;

    private ConcertScheduleRepository concertScheduleRepository;

    public ConcertService(ConcertClassRepository concertClassRepository, ConcertScheduleRepository concertScheduleRepository) {
        this.concertClassRepository = concertClassRepository;
        this.concertScheduleRepository = concertScheduleRepository;
    }

    public ConcertClassVo addConcertClass(Integer concertId,ConcertClassBody concertClassBody) {
        ConcertSchedule concertSchedule = concertScheduleRepository.findByConcertId(concertId);
        if (concertSchedule == null) {
            throw new EntityNotExistException("ConcertSchedule");
        }
        Date startDate = stringToDate(concertSchedule.getStartTime());
        if (startDate.before(getCurrentTime())) {
            throw new ConcertInProgressException();
        }
        ConcertClass concertClass = initConcertClass(concertId, concertClassBody);
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

    private ConcertClass initConcertClass(Integer concertId, ConcertClassBody concertClassBody) {
        ConcertClass concertClass = new ConcertClass();
        concertClass.setClassName(concertClassBody.getClassName());
        concertClass.setCapacity(concertClassBody.getCapacity());
        concertClass.setPriceInUsd(generatePriceInUsd(concertClassBody));
        concertClass.setPriceInLocalCurr(concertClassBody.getPrice());
        concertClass.setAvailableSeats(0);
        concertClass.setCurrency(concertClass.getCurrency());
        concertClass.setConcertId(concertId);
        return concertClass;
    }

    private double generatePriceInUsd(ConcertClassBody concertClassBody) {
        String currency = concertClassBody.getCurrency();
        return switch (currency) {
            case "USD" -> concertClassBody.getPrice();
            case "CNY" -> concertClassBody.getPrice() / 7d;
            case "HKD" -> concertClassBody.getPrice() / 7.8d;
            default -> concertClassBody.getPrice() / 6.8d;
        };
    }

    public List<ConcertClassVo> listConcertClasses(Integer concertId) {
        return concertClassRepository.findByConcertId(concertId).stream()
                .map(this::buildConcertClassVo)
                .toList();
    }
}
