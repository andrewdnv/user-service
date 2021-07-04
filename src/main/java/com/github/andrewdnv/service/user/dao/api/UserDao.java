package com.github.andrewdnv.service.user.dao.api;

import com.github.andrewdnv.service.user.entity.UserDo;

import java.util.Optional;

public interface UserDao {

    Optional<UserDo> getUser(Long id);

    UserDo createUser(UserDo userDo);

    UserDo updateUser(UserDo userDo);

    boolean deleteUser(Long id);

}