package com.oocl.ita.web.service.impl;

import com.oocl.ita.web.repository.SysUserRepository;
import com.oocl.ita.web.domain.po.SysUser;
import com.oocl.ita.web.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserRepository userDAO;

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public List<SysUser> selectUserByEmail(String userName) {
        return userDAO.findByEmail(userName);
    }
}
