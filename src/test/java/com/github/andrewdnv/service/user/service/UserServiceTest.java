package com.github.andrewdnv.service.user.service;

import com.github.andrewdnv.service.user.MockObjects;
import com.github.andrewdnv.service.user.dao.api.UserDao;
import com.github.andrewdnv.service.user.exception.NotFoundException;
import com.github.andrewdnv.service.user.exception.UnexpectedException;
import com.github.andrewdnv.service.user.mapper.CommonMapper;
import com.github.andrewdnv.service.user.mapper.api.UserMapper;
import com.github.andrewdnv.service.user.mapper.api.UserMapperImpl;
import com.github.andrewdnv.service.user.service.api.UserService;
import com.github.andrewdnv.service.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("unitTest")
public class UserServiceTest {

    private UserDao userDao;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userDao = Mockito.mock(UserDao.class);

        UserMapper userMapper = new UserMapperImpl();
        CommonMapper commonMapper = new CommonMapper();
        ReflectionTestUtils.setField(userMapper, "commonMapper", commonMapper);

        userService = new UserServiceImpl(userDao, userMapper);
    }

    @Test
    public void testGetUser() {
        when(userDao.getUser(any())).thenReturn(Optional.of(MockObjects.userDo()));

        assertThat(userService.getUser(1L)).isEqualTo(MockObjects.user());

        verify(userDao, times(1)).getUser(eq(1L));
    }

    @Test
    public void testGetUser_NotFound() {
        when(userDao.getUser(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUser(1L));

        verify(userDao, times(1)).getUser(eq(1L));
    }

    @Test
    public void testCreateUser() {
        when(userDao.createUser(any())).thenReturn(Optional.of(MockObjects.userDo()));

        assertThat(userService.createUser(MockObjects.user())).isEqualTo(MockObjects.user());

        verify(userDao, times(1)).createUser(any());
    }

    @Test
    public void testCreateUser_Unexpected() {
        when(userDao.createUser(any())).thenReturn(Optional.empty());

        assertThrows(UnexpectedException.class, () -> userService.createUser(MockObjects.user()));

        verify(userDao, times(1)).createUser(any());
    }

    @Test
    public void testUpdateUser() {
        when(userDao.updateUser(any())).thenReturn(Optional.of(MockObjects.userDo()));

        assertThat(userService.updateUser(MockObjects.user())).isEqualTo(MockObjects.user());

        verify(userDao, times(1)).updateUser(any());
    }

    @Test
    public void testUpdateUser_Unexpected() {
        when(userDao.updateUser(any())).thenReturn(Optional.empty());

        assertThrows(UnexpectedException.class, () -> userService.updateUser(MockObjects.user()));

        verify(userDao, times(1)).updateUser(any());
    }

    @Test
    public void testDeleteUser() {
        when(userDao.deleteUser(any())).thenReturn(true);

        userService.deleteUser(1L);

        verify(userDao, times(1)).deleteUser(eq(1L));
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userDao.deleteUser(any())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(1L));

        verify(userDao, times(1)).deleteUser(eq(1L));
    }

}