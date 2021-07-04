package com.github.andrewdnv.service.user.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class UserDo {

    private Long id;

    private String firstName;
    private String patronymicName;
    private String lastName;

    private Date dateOfBirth;
    private Date dateOfRegistration;

    private String city;
    private String mobilePhone;
    private String email;

    private String status;

}