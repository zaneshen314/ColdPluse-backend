package com.oocl.ita.web.service;


import com.oocl.ita.web.common.utils.SecurityUtils;
import com.oocl.ita.web.core.exception.EmailExistException;
import com.oocl.ita.web.core.redis.RedisCache;
import com.oocl.ita.web.core.security.context.AuthenticationContextHolder;
import com.oocl.ita.web.core.security.domain.LoginUser;
import com.oocl.ita.web.core.security.service.TokenService;
import com.oocl.ita.web.domain.bo.Login.RegisterBody;
import com.oocl.ita.web.domain.po.User.User;
import com.oocl.ita.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录校验方法
 * 

 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository sysUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;


    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password)
    {
        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(authenticationToken);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Login failed");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 获取登录用户信息
     */
    public User getLoginUser() {
        Integer userId = SecurityUtils.getUserId();
        return sysUserRepository.getById(userId);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }

    public void register(RegisterBody registerBody) {
        String email = registerBody.getEmail();
        // 校验邮箱是否存在
        List<User> userList = sysUserRepository.findByEmail(email);
        if (CollectionUtils.isEmpty(userList)) {

            User entity = registerBody.toEntity();
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            sysUserRepository.save(entity);
        } else {
            throw new EmailExistException();
        }
    }

}
