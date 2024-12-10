package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.po.CharityEvent;
import com.oocl.ita.web.domain.po.CharityEventParticipation;
import com.oocl.ita.web.domain.vo.charity.UserCharityEventParticipationResp;
import com.oocl.ita.web.repository.CharityEventParticipationRepository;
import com.oocl.ita.web.service.CharityEventService;
import com.oocl.ita.web.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CharityEventService charityEventService;
    private final CharityEventParticipationRepository charityEventParticipationRepository;

    public UserController(UserService userService, CharityEventService charityEventService, CharityEventParticipationRepository charityEventParticipationRepository) {
        this.userService = userService;
        this.charityEventService = charityEventService;
        this.charityEventParticipationRepository = charityEventParticipationRepository;
    }

    @GetMapping("/{userId}/cumulatedPoint")
    public Integer getUserCumulatedPoint(@PathVariable Integer userId) {
        return userService.selectUserCumulatedPoint(userId);
    }

    @GetMapping("/{userId}/charity-event-participations")
    public List<UserCharityEventParticipationResp> getCharityEventParticipationByUserId(@PathVariable Integer userId) {
        List<CharityEventParticipation> charityEventParticipations = charityEventService.getCharityEventParticipationByUserid(userId);

        return charityEventParticipations
                .stream()
                .map(charityEventParticipation -> {
                    CharityEvent charityEvent = charityEventService.getById(charityEventParticipation.getCharityEventId());
                    return new UserCharityEventParticipationResp(charityEventParticipation, charityEvent);
                }).toList();
    }

    @GetMapping("/{userId}/charity-events")
    public List<Integer> getCharityEventIdsByUserId(@PathVariable Integer userId) {
        return charityEventParticipationRepository.findAllByUserId(userId).stream().map(CharityEventParticipation::getCharityEventId).toList();
    }


}
