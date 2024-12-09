package com.oocl.ita.web.service;

import com.oocl.ita.web.domain.po.User;

import java.util.List;

public interface ISysUserService {

    /**
     * 通过用户名查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    public List<User> selectUserByEmail(String email);
}
