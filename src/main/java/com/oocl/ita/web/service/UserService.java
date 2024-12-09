package com.oocl.ita.web.service;

import com.oocl.ita.web.domain.po.User;
import com.oocl.ita.web.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUserCumulatedPoint(Integer userId, Integer point) {
        User user = userRepository.getById(userId);
        user.setCumulatedPoint(user.getCumulatedPoint() + point);
        return userRepository.save(user);
    }
}
