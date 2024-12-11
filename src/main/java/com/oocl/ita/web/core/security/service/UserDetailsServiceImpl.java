package com.oocl.ita.web.core.security.service;

import com.oocl.ita.web.domain.po.User.User;
import com.oocl.ita.web.core.security.context.AuthenticationContextHolder;
import com.oocl.ita.web.core.security.domain.LoginUser;
import com.oocl.ita.web.common.utils.SecurityUtils;
import com.oocl.ita.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户验证处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        List<User> user = userService.selectUserByEmail(email);
        if (CollectionUtils.isEmpty(user))
        {
            log.info("登录用户：{} 不存在.", email);
            throw new RuntimeException("登录用户：" + email + " 不存在");
        }
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();
        if (!SecurityUtils.matchesPassword(password, user.get(0).getPassword()))
        {
            log.info("登录用户：{} 密码错误.", email);
            throw new RuntimeException("密码错误");
        }
        return createLoginUser(user.get(0));
    }

    public UserDetails createLoginUser(User user)
    {
        return new LoginUser(user.getId(), user);
    }
}
