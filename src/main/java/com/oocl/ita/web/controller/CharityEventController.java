package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.CharityEventRegBody;
import com.oocl.ita.web.domain.bo.CharityEventUpdateBody;
import com.oocl.ita.web.domain.po.CharityEvent;
import com.oocl.ita.web.domain.po.CharityEventParticipation;
import com.oocl.ita.web.domain.vo.CharityEventParticipationsResp;
import com.oocl.ita.web.service.CharityEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/charity-event")
public class CharityEventController {

    private final CharityEventService charityEventService;

    public CharityEventController(CharityEventService charityEventService) {
        this.charityEventService = charityEventService;
    }

    @GetMapping
    public List<CharityEvent> getAllCharityEvents() {
        return charityEventService.getAllCharityEvent();
    }

    @GetMapping("/participation/{userId}")
    public List<CharityEventParticipation> getCharityEventParticipationByUserId(@PathVariable Integer userId) {
        return charityEventService.getCharityEventParticipationByUserid(userId);
    }

    @GetMapping("/participation/{eventId}")
    public CharityEventParticipationsResp getCharityEventParticipationByEventId(@PathVariable Integer eventId) {
        return charityEventService.getCharityEventParticipationByCharityEventId(eventId);
    }

    @PostMapping("/register")
    public CharityEventParticipation registerCharityEvent(@RequestBody CharityEventRegBody charityEventRegBody) {
        return charityEventService.registerCharityEvent(charityEventRegBody.getUserId(), charityEventRegBody.getCharityEventId(), charityEventRegBody.isClaimPoint());
    }

    @PostMapping("/enroll")
    public CharityEventParticipation enrollCharityEventParticipation(@RequestBody CharityEventUpdateBody charityEventUpdateBody) {
        return charityEventService.enrollCharityEventParticipation(charityEventUpdateBody.getUserId(), charityEventUpdateBody.getCharityEventId());
    }

    @PostMapping("/finish")
    public CharityEventParticipation finishCharityEventParticipation(@RequestBody CharityEventUpdateBody charityEventUpdateBody) {
        return charityEventService.finishCharityEventParticipation(charityEventUpdateBody.getUserId(), charityEventUpdateBody.getCharityEventId());
    }

    @PostMapping("/close")
    public CharityEventParticipation closeCharityEventParticipation(@RequestBody CharityEventUpdateBody charityEventUpdateBody) {
        return charityEventService.closeCharityEventParticipation(charityEventUpdateBody.getUserId(), charityEventUpdateBody.getCharityEventId());
    }

}
