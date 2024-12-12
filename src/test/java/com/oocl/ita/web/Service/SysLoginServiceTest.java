package com.oocl.ita.web.Service;

import com.oocl.ita.web.core.exception.EmailExistException;
import com.oocl.ita.web.core.redis.RedisCache;
import com.oocl.ita.web.core.security.context.AuthenticationContextHolder;
import com.oocl.ita.web.core.security.domain.LoginUser;
import com.oocl.ita.web.core.security.service.TokenService;
import com.oocl.ita.web.domain.bo.Login.RegisterBody;
import com.oocl.ita.web.domain.po.User.User;
import com.oocl.ita.web.repository.UserRepository;
import com.oocl.ita.web.service.SysLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SysLoginServiceTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository sysUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisCache redisCache;

    @InjectMocks
    private SysLoginService sysLoginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_valid_credentials_when_login_then_return_token() {
        // given
        String username = "testUser";
        String password = "testPass";
        Authentication authentication = mock(Authentication.class);
        LoginUser loginUser = mock(LoginUser.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(loginUser);
        when(tokenService.createToken(loginUser)).thenReturn("testToken");

        // when
        String token = sysLoginService.login(username, password);

        // then
        assertEquals("testToken", token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).createToken(loginUser);
    }

    @Test
    void given_invalid_credentials_when_login_then_throw_exception() {
        // given
        String username = "testUser";
        String password = "testPass";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Login failed"));

        // when & then
        assertThrows(RuntimeException.class, () -> sysLoginService.login(username, password));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void given_valid_register_body_when_register_then_save_user() {
        // given
        RegisterBody registerBody = new RegisterBody();
        registerBody.setEmail("test@example.com");
        registerBody.setPassword("testPass");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPass");

        when(sysUserRepository.findByEmail("test@example.com")).thenReturn(Collections.emptyList());
        when(passwordEncoder.encode("testPass")).thenReturn("encodedPass");
        when(sysUserRepository.save(any(User.class))).thenReturn(user);

        // when
        sysLoginService.register(registerBody);

        // then
        verify(sysUserRepository).findByEmail("test@example.com");
        verify(passwordEncoder).encode("testPass");
        verify(sysUserRepository).save(any(User.class));
    }

    @Test
    void given_existing_email_when_register_then_throw_email_exist_exception() {
        // given
        RegisterBody registerBody = new RegisterBody();
        registerBody.setEmail("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        when(sysUserRepository.findByEmail("test@example.com")).thenReturn(List.of(user));

        // when & then
        assertThrows(EmailExistException.class, () -> sysLoginService.register(registerBody));
        verify(sysUserRepository).findByEmail("test@example.com");
    }
}