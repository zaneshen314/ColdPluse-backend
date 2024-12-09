package com.oocl.ita.web.controller;

import com.oocl.ita.web.domain.bo.LoginBody;
import com.oocl.ita.web.domain.bo.RegisterBody;
import com.oocl.ita.web.domain.po.User;
import com.oocl.ita.web.domain.vo.RespBean;
import com.oocl.ita.web.core.redis.RedisCache;
import com.oocl.ita.web.core.security.service.TokenService;
import com.oocl.ita.web.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登录验证 TODO 这些注册登录接口放在这里了
 */
@RestController
public class SysLoginController {

    private final SysLoginService loginService;

    public SysLoginController(SysLoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public RespBean<String> login(@RequestBody LoginBody loginBody) {
        // 生成令牌
        String token = loginService.login(loginBody.getEmail(), loginBody.getPassword());
        return RespBean.success(token);
    }

    /**
     * 註冊
     *
     * @return 登录者信息
     */
    @PostMapping("/register")
    public RespBean<String> register(@RequestBody RegisterBody registerBody) {
        loginService.register(registerBody);
        return RespBean.success();
    }

    /**
     * 获取登录者信息
     *
     * @return 登录者信息
     */
    @GetMapping("/userinfo")
    public RespBean<User> login() {
        return RespBean.success(loginService.getLoginUser());
    }



}
