package com.oocl.ita.web.controller;

import com.oocl.ita.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/cumulatedPoint")
    public Integer getUserCumulatedPoint(@PathVariable Integer userId) {
        return userService.selectUserCumulatedPoint(userId);
    }



}
