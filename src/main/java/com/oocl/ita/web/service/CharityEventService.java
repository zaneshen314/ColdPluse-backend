package com.oocl.ita.web.service;

import com.oocl.ita.web.domain.po.CharityEvent;
import com.oocl.ita.web.domain.po.CharityEventParticipation;
import com.oocl.ita.web.repository.CharityEventParticipationRepository;
import com.oocl.ita.web.repository.CharityEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharityEventService {

    private final CharityEventRepository charityEventRepository;

    private CharityEventParticipationRepository charityEventParticipationRepository;

    public CharityEventService(CharityEventRepository charityEventRepository, CharityEventParticipationRepository charityEventParticipationRepository) {
        this.charityEventRepository = charityEventRepository;
        this.charityEventParticipationRepository = charityEventParticipationRepository;
    }


    public List<CharityEvent> getAllCharityEvent() {
        return charityEventRepository.findAll();
    }

    public List<CharityEventParticipation> getCharityEventParticipationByUserid(Integer userId) {
        return charityEventParticipationRepository.findAllByUserId(userId);
    }



}
