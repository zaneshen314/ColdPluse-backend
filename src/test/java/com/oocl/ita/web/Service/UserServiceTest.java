package com.oocl.ita.web.Service;

import com.oocl.ita.web.domain.po.User.User;
import com.oocl.ita.web.repository.UserRepository;
import com.oocl.ita.web.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_valid_email_when_select_user_by_email_then_return_user_list() {
        // given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(List.of(user));

        // when
        List<User> users = userService.selectUserByEmail(email);

        // then
        assertEquals(1, users.size());
        assertEquals(email, users.get(0).getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void given_valid_user_id_and_point_when_update_user_cumulated_point_then_update_point() {
        // given
        Integer userId = 1;
        Integer point = 10;
        User user = new User();
        user.setCumulatedPoint(20);
        when(userRepository.getById(userId)).thenReturn(user);

        // when
        userService.updateUserCumulatedPoint(userId, point);

        // then
        assertEquals(30, user.getCumulatedPoint());
        verify(userRepository).getById(userId);
        verify(userRepository).save(user);
    }

    @Test
    void given_valid_user_id_when_select_user_cumulated_point_then_return_point() {
        // given
        Integer userId = 1;
        User user = new User();
        user.setCumulatedPoint(20);
        when(userRepository.getById(userId)).thenReturn(user);

        // when
        Integer point = userService.selectUserCumulatedPoint(userId);

        // then
        assertEquals(20, point);
        verify(userRepository).getById(userId);
    }

    @Test
    void given_valid_user_id_when_get_by_id_then_return_user() {
        // given
        Integer userId = 1;
        User user = new User();
        when(userRepository.getById(userId)).thenReturn(user);

        // when
        User result = userService.getById(userId);

        // then
        assertEquals(user, result);
        verify(userRepository).getById(userId);
    }

    @Test
    void given_insufficient_cumulated_point_when_use_user_cumulated_point_then_throw_exception() {
        // given
        Integer userId = 1;
        Integer cumulatedPointCharged = 30;
        User user = new User();
        user.setCumulatedPoint(20);
        when(userRepository.getById(userId)).thenReturn(user);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userService.useUserCumulatedPoint(userId, cumulatedPointCharged));
        verify(userRepository).getById(userId);
    }
}