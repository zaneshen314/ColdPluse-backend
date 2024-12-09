package com.oocl.ita.web.service;

import com.oocl.ita.web.repository.SysUserRepository;
import com.oocl.ita.web.domain.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysUserService{

    @Autowired
    private SysUserRepository sysUserRepository;

    public SysUserService(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public List<User> selectUserByEmail(String userName) {
        return sysUserRepository.findByEmail(userName);
    }
}
