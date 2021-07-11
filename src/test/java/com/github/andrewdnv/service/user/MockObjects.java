package com.github.andrewdnv.service.user;

import com.github.andrewdnv.service.user.entity.UserDo;
import com.github.andrewdnv.service.user.model.User;
import com.github.andrewdnv.service.user.model.UserStatus;

import java.sql.Date;
import java.time.LocalDate;

public class MockObjects {

    public static User user() {
        return new User()
            .setUserId(1L)
            .setFirstName("firstName")
            .setPatronymic("patronymic")
            .setLastName("lastName")
            .setBirthDate(LocalDate.of(1990, 1, 24))
            .setRegistrationDate(LocalDate.of(2020, 1, 24))
            .setCity("city")
            .setMobilePhone("+71001001010")
            .setEmail("user@mail.com")
            .setStatus(UserStatus.ACTIVE);
    }

    public static UserDo userDo() {
        return new UserDo()
            .setId(1L)
            .setFirstName("firstName")
            .setLastName("lastName")
            .setPatronymicName("patronymic")
            .setDateOfBirth(Date.valueOf(LocalDate.of(1990, 1, 24)))
            .setDateOfRegistration(Date.valueOf(LocalDate.of(2020, 1, 24)))
            .setCity("city")
            .setMobilePhone("+71001001010")
            .setEmail("user@mail.com")
            .setStatus(UserStatus.ACTIVE.getValue());
    }

}