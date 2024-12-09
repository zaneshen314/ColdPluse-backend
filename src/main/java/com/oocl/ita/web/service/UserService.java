package com.oocl.ita.web.service;

import com.oocl.ita.web.domain.po.User;
import com.oocl.ita.web.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public List<User> selectUserByEmail(String userName) {
        return userRepository.findByEmail(userName);
    }

    public void updateUserCumulatedPoint(Integer userId, Integer point) {
        User user = userRepository.getById(userId);
        user.setCumulatedPoint(user.getCumulatedPoint() + point);
        userRepository.save(user);
    }

    public Integer selectUserCumulatedPoint(Integer userId) {
        return userRepository.getById(userId).getCumulatedPoint();
    }
}
