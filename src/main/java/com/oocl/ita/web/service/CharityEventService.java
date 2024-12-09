package com.oocl.ita.web.service;

import com.oocl.ita.web.domain.po.CharityEvent;
import com.oocl.ita.web.domain.po.CharityEventParticipation;
import com.oocl.ita.web.domain.po.CharityEventParticipationKey;
import com.oocl.ita.web.repository.CharityEventParticipationRepository;
import com.oocl.ita.web.repository.CharityEventRepository;
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

    public CharityEventParticipation registerCharityEvent(Integer userId, Integer charityEventId, boolean claimPoint) {
        return charityEventParticipationRepository.save(new CharityEventParticipation(userId, charityEventId, false, false, false, claimPoint));
    }

    public CharityEventParticipation enrollCharityEventParticipation(Integer userId, Integer charityEventId) {
        CharityEventParticipation charityEventParticipation = charityEventParticipationRepository.getById(new CharityEventParticipationKey(userId, charityEventId));
        charityEventParticipation.setEnrolled(true);
        return charityEventParticipationRepository.save(charityEventParticipation);
    }

    public CharityEventParticipation finishCharityEventParticipation(Integer userId, Integer charityEventId) {
        CharityEventParticipation charityEventParticipation = charityEventParticipationRepository.getById(new CharityEventParticipationKey(userId, charityEventId));
        charityEventParticipation.setFinished(true);
        return charityEventParticipationRepository.save(charityEventParticipation);
    }

    public CharityEventParticipation closeCharityEventParticipation(Integer userId, Integer charityEventId) {
        CharityEventParticipation charityEventParticipation = charityEventParticipationRepository.getById(new CharityEventParticipationKey(userId, charityEventId));
        charityEventParticipation.setClosed(true);
        return charityEventParticipationRepository.save(charityEventParticipation);
    }


}
