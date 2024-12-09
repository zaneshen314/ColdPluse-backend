package com.oocl.ita.web.controller;

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
    public CharityEventParticipation registerCharityEvent(@RequestParam Integer userId, @RequestParam Integer charityEventId, @RequestParam boolean claimPoint) {
        return charityEventService.registerCharityEvent(userId, charityEventId, claimPoint);
    }

    @PostMapping("/enroll")
    public CharityEventParticipation enrollCharityEventParticipation(@RequestParam Integer userId, @RequestParam Integer charityEventId) {
        return charityEventService.enrollCharityEventParticipation(userId, charityEventId);
    }

    @PostMapping("/finish")
    public CharityEventParticipation finishCharityEventParticipation(@RequestParam Integer userId, @RequestParam Integer charityEventId) {
        return charityEventService.finishCharityEventParticipation(userId, charityEventId);
    }

    @PostMapping("/close")
    public CharityEventParticipation closeCharityEventParticipation(@RequestParam Integer userId, @RequestParam Integer charityEventId) {
        return charityEventService.closeCharityEventParticipation(userId, charityEventId);
    }

}
