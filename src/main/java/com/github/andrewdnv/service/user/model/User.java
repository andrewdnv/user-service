package com.github.andrewdnv.service.user.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private Long userId;

    private String firstName;
    private String patronymic;
    private String lastName;

    private LocalDate birthDate;
    private LocalDate registrationDate;

    private String city;
    private String mobilePhone;
    private String email;

    private UserStatus status;

}