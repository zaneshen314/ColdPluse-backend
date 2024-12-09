package com.oocl.ita.web.service;

import com.oocl.ita.web.domain.bo.ConcertClassBody;
import com.oocl.ita.web.domain.po.ConcertClass;
import com.oocl.ita.web.domain.vo.ConcertClassVo;
import com.oocl.ita.web.repository.ConcertClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private ConcertClassRepository concertClassRepository;

    public ConcertService(ConcertClassRepository concertClassRepository) {
        this.concertClassRepository = concertClassRepository;
    }

    public ConcertClassVo addConcertClass(ConcertClassBody concertClassBody) {
        ConcertClass concertClass = initConcertClass(concertClassBody);
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
        return concertClassVo;
    }

    private ConcertClass initConcertClass(ConcertClassBody concertClassBody) {
        ConcertClass concertClass = new ConcertClass();
        concertClass.setClassName(concertClassBody.getClassName());
        concertClass.setCapacity(concertClassBody.getCapacity());
        concertClass.setPriceInUsd(generatePriceInUsd(concertClassBody));
        concertClass.setPriceInLocalCurr(concertClassBody.getPrice());
        concertClass.setAvailableSeats(0);
        concertClass.setCurrency(concertClass.getCurrency());
        concertClass.setConcertId(concertClassBody.getConcertId());
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

    public List<ConcertClassVo> listConcertClasses() {
        return concertClassRepository.findAll().stream()
                .map(this::buildConcertClassVo)
                .toList();
    }
}
