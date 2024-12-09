package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.po.CharityEventParticipation;
import com.oocl.ita.web.service.CharityEventService;
import com.oocl.ita.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public UserController(UserService userService, CharityEventService charityEventService) {
        this.userService = userService;
        this.charityEventService = charityEventService;
    }

    @GetMapping("/{userId}/cumulatedPoint")
    public Integer getUserCumulatedPoint(@PathVariable Integer userId) {
        return userService.selectUserCumulatedPoint(userId);
    }

    @GetMapping("/{userId}/charity-event-participations")
    public List<CharityEventParticipation> getCharityEventParticipationByUserId(@PathVariable Integer userId) {
        return charityEventService.getCharityEventParticipationByUserid(userId);
    }


}
