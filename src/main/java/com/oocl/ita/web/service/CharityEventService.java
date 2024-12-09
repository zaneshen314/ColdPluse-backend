package com.oocl.ita.web.service;

import com.oocl.ita.web.CharityEventParticipationStatus;
import com.oocl.ita.web.domain.po.CharityEvent;
import com.oocl.ita.web.domain.po.CharityEventParticipation;
import com.oocl.ita.web.domain.po.key.CharityEventParticipationKey;
import com.oocl.ita.web.repository.CharityEventParticipationRepository;
import com.oocl.ita.web.repository.CharityEventRepository;
import com.oocl.ita.web.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.oocl.ita.web.CharityEventParticipationStatus.REGISTERED;

@Service
public class CharityEventService {

    private final CharityEventRepository charityEventRepository;

    private final CharityEventParticipationRepository charityEventParticipationRepository;

    private final UserService userService;

    public CharityEventService(CharityEventRepository charityEventRepository, CharityEventParticipationRepository charityEventParticipationRepository, UserService userService, UserRepository userRepository) {
        this.charityEventRepository = charityEventRepository;
        this.charityEventParticipationRepository = charityEventParticipationRepository;
        this.userService = userService;
    }


    public List<CharityEvent> getAllCharityEvent() {
        return charityEventRepository.findAll();
    }

    public List<CharityEventParticipation> getCharityEventParticipationByUserid(Integer userId) {
        return charityEventParticipationRepository.findAllByUserId(userId);
    }

    public CharityEventParticipation registerCharityEvent(Integer userId, Integer charityEventId, boolean claimPoint) {
        return charityEventParticipationRepository.save(new CharityEventParticipation(userId, charityEventId, REGISTERED, claimPoint));
    }

    public List<CharityEventParticipation> getCharityEventParticipationByCharityEventId(Integer charityEventId) {
        return charityEventParticipationRepository.findAllByCharityEventId(charityEventId);
    }


    public CharityEventParticipation updateCharityEventParticipationStatus(Integer userId, Integer charityEventId, CharityEventParticipationStatus status) {
        CharityEventParticipation charityEventParticipation = charityEventParticipationRepository.getById(new CharityEventParticipationKey(userId, charityEventId));
        charityEventParticipation.setStatus(status);
        processUserClaimPoint(userId, charityEventId, status, charityEventParticipation);
        return charityEventParticipationRepository.save(charityEventParticipation);
    }

    private void processUserClaimPoint(Integer userId, Integer charityEventId, CharityEventParticipationStatus status, CharityEventParticipation charityEventParticipation) {
        if (status == CharityEventParticipationStatus.COMPLETED && charityEventParticipation.isClaimPoint()) {
            userService.updateUserCumulatedPoint(userId, charityEventRepository.getById(charityEventId).getPoint());
        }
    }

    public CharityEvent getById(Integer id) {
        return charityEventRepository.getById(id);
    }
}
