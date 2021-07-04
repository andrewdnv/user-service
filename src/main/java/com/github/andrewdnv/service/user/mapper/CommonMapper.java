package com.github.andrewdnv.service.user.mapper;

import java.sql.Date;
import java.time.LocalDate;

public class CommonMapper {

    public Date toDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public LocalDate toLocalDate(Date date) {
        return date.toLocalDate();
    }

}