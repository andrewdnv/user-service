package com.github.andrewdnv.service.user.mapper;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class CommonMapper {

    public Date toDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public LocalDate toLocalDate(Date date) {
        return date.toLocalDate();
    }

}