package com.github.andrewdnv.service.user.service.impl;

import com.github.andrewdnv.service.user.dao.api.UserDao;
import com.github.andrewdnv.service.user.entity.UserDo;
import com.github.andrewdnv.service.user.exception.NotFoundException;
import com.github.andrewdnv.service.user.mapper.UserMapper;
import com.github.andrewdnv.service.user.model.User;
import com.github.andrewdnv.service.user.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        Optional<UserDo> userDoOptional = userDao.getUser(id);
        if (userDoOptional.isEmpty()) {
            throw new NotFoundException("User with id " + id + " is not found");
        }
        return userMapper.toUser(userDoOptional.get());
    }

    @Override
    @Transactional
    public User createUser(User user) {
        UserDo userDo = userMapper.toUserDo(user);
        UserDo savedUserDo = userDao.createUser(userDo);
        return userMapper.toUser(savedUserDo);
    }

    @Override
    @Transactional
    public User createOrUpdateUser(User user) {
        Optional<UserDo> userDoOptional = userDao.getUser(user.getUserId());
        UserDo userDo = userMapper.toUserDo(user);
        UserDo savedUserDo;
        if (userDoOptional.isEmpty()) {
            savedUserDo = userDao.createUser(userDo);
        } else {
            savedUserDo = userDao.updateUser(userDo);
        }
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