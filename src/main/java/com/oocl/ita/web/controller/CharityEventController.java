package com.oocl.ita.web.controller;

import com.oocl.ita.web.common.utils.SecurityUtils;
import com.oocl.ita.web.domain.bo.Charity.CharityEventRegBody;
import com.oocl.ita.web.domain.bo.Charity.CharityEventUpdateBody;
import com.oocl.ita.web.domain.po.Charity.CharityEvent;
import com.oocl.ita.web.domain.po.Charity.CharityEventParticipation;
import com.oocl.ita.web.domain.po.User.User;
import com.oocl.ita.web.domain.vo.User.UserVo;
import com.oocl.ita.web.domain.vo.charity.CharityEventParticipationsResp;
import com.oocl.ita.web.domain.vo.charity.UserParticipationRecResp;
import com.oocl.ita.web.service.CharityEventService;
import com.oocl.ita.web.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/charity-events")
public class CharityEventController {

    private final CharityEventService charityEventService;
    private final UserService userService;

    public CharityEventController(CharityEventService charityEventService, UserService userService) {
        this.charityEventService = charityEventService;
        this.userService = userService;
    }

    @GetMapping
    public List<CharityEvent> getAllCharityEvents() {
        return charityEventService.getAllCharityEvent();
    }

    @GetMapping(params = "eventId")
    public CharityEventParticipationsResp getCharityEventParticipationByEventId(@RequestParam Integer eventId) {
        CharityEvent charityEvent = charityEventService.getById(eventId);
        List<CharityEventParticipation> charityEventParticipations = charityEventService.getCharityEventParticipationByCharityEventId(eventId);
        List<UserParticipationRecResp> userParticipationRecResps = charityEventParticipations
                .stream()
                .map(charityEventParticipation ->{
                    User user = userService.getById(charityEventParticipation.getUserId());
                    UserVo userVo = new UserVo(user.getId(), user.getName(), user.getEmail(), user.getCumulatedPoint());
                    return new UserParticipationRecResp(userVo, charityEventParticipation);
                }

                ).toList();
        return new CharityEventParticipationsResp(charityEvent, userParticipationRecResps);
    }

    @PostMapping
    public CharityEventParticipation registerCharityEvent(@RequestBody CharityEventRegBody charityEventRegBody) {
        Integer userId = SecurityUtils.getUserId();
        return charityEventService.registerCharityEvent(userId, charityEventRegBody.getCharityEventId(), charityEventRegBody.isClaimPoint());
    }

    @PutMapping("/status")
    public CharityEventParticipation updateCharityEventParticipationStatus(@RequestBody CharityEventUpdateBody charityEventUpdateBody) {
        return charityEventService.updateCharityEventParticipationStatus(charityEventUpdateBody.getUserId(), charityEventUpdateBody.getCharityEventId(), charityEventUpdateBody.getStatus());
    }

    @DeleteMapping(params = {"eventId"})
    public void deleteCharityEventParticipation(@RequestParam Integer eventId) {
        Integer userId = SecurityUtils.getUserId();
        charityEventService.quitCharityEvent(eventId, userId);
    }
}
