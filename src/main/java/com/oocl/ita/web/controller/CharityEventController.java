package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.CharityEventRegBody;
import com.oocl.ita.web.domain.bo.CharityEventUpdateBody;
import com.oocl.ita.web.domain.po.CharityEvent;
import com.oocl.ita.web.domain.po.CharityEventParticipation;
import com.oocl.ita.web.domain.vo.CharityEventParticipationsResp;
import com.oocl.ita.web.domain.vo.UserCharityEventParticipationResp;
import com.oocl.ita.web.service.CharityEventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/charity-events")
public class CharityEventController {

    private final CharityEventService charityEventService;

    public CharityEventController(CharityEventService charityEventService) {
        this.charityEventService = charityEventService;
    }

    @GetMapping
    public List<CharityEvent> getAllCharityEvents() {
        return charityEventService.getAllCharityEvent();
    }

    @GetMapping(params = "eventId")
    public CharityEventParticipationsResp getCharityEventParticipationByEventId(@RequestParam Integer eventId) {
        CharityEvent charityEvent = charityEventService.getById(eventId);
        List<CharityEventParticipation> charityEventParticipations = charityEventService.getCharityEventParticipationByCharityEventId(eventId);
        return new CharityEventParticipationsResp(charityEvent.getId(), charityEvent.getName(), charityEventParticipations);
    }

    @PostMapping
    public CharityEventParticipation registerCharityEvent(@RequestBody CharityEventRegBody charityEventRegBody) {
        return charityEventService.registerCharityEvent(charityEventRegBody.getUserId(), charityEventRegBody.getCharityEventId(), charityEventRegBody.isClaimPoint());
    }

    @PutMapping("/status")
    public CharityEventParticipation updateCharityEventParticipationStatus(@RequestBody CharityEventUpdateBody charityEventUpdateBody) {
        return charityEventService.updateCharityEventParticipationStatus(charityEventUpdateBody.getUserId(), charityEventUpdateBody.getCharityEventId(), charityEventUpdateBody.getStatus());
    }

}
