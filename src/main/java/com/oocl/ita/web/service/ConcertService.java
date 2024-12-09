package com.oocl.ita.web.service;

import com.oocl.ita.web.core.exception.ConcertInProgressException;
import com.oocl.ita.web.core.exception.EntityNotExistException;
import com.oocl.ita.web.domain.bo.ConcertClassBody;
import com.oocl.ita.web.domain.bo.ConcertClassUpdateBody;
import com.oocl.ita.web.domain.po.ConcertClass;
import com.oocl.ita.web.domain.po.ConcertSchedule;
import com.oocl.ita.web.domain.vo.ConcertClassVo;
import com.oocl.ita.web.repository.ConcertClassRepository;
import com.oocl.ita.web.repository.ConcertScheduleRepository;
import org.springframework.stereotype.Service;

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
}
