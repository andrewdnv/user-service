package com.github.andrewdnv.service.user.service.api;

import com.github.andrewdnv.service.user.model.User;

public interface UserService {

    User getUser(Long id);

    User createUser(User user);

    User createOrUpdateUser(User user);

    void deleteUser(Long id);

}