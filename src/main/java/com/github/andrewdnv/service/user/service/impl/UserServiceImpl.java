package com.github.andrewdnv.service.user.service.impl;

import com.github.andrewdnv.service.user.dao.api.UserDao;
import com.github.andrewdnv.service.user.entity.UserDo;
import com.github.andrewdnv.service.user.exception.NotFoundException;
import com.github.andrewdnv.service.user.exception.UnexpectedException;
import com.github.andrewdnv.service.user.mapper.api.UserMapper;
import com.github.andrewdnv.service.user.model.User;
import com.github.andrewdnv.service.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        UserDo userDo = userDao.getUser(id)
            .orElseThrow(() -> new NotFoundException("User with id " + id + " is not found"));
        return userMapper.toUser(userDo);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        UserDo userDo = userMapper.toUserDo(user);
        UserDo savedUserDo = userDao.createUser(userDo)
            .orElseThrow(() -> new UnexpectedException("User creation error"));
        return userMapper.toUser(savedUserDo);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        UserDo userDo = userMapper.toUserDo(user);
        UserDo savedUserDo = userDao.updateUser(userDo)
            .orElseThrow(() -> new UnexpectedException("User update error"));
        return userMapper.toUser(savedUserDo);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userDao.deleteUser(id)) {
            throw new NotFoundException("User with id " + id + " is not found");
        }
    }

}