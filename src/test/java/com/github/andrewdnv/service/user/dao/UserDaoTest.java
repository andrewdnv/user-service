package com.github.andrewdnv.service.user.dao;

import com.github.andrewdnv.service.user.MockObjects;
import com.github.andrewdnv.service.user.dao.api.UserDao;
import com.github.andrewdnv.service.user.entity.UserDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
@ActiveProfiles("unitTest")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    @Sql(statements = {
        "INSERT INTO users (id, first_name, last_name, patronymic_name, " +
        "date_of_birth, date_of_registration, city, mobile_phone, email, status) " +
        "VALUES (1, 'firstName', 'lastName', 'patronymic', " +
        "'1990-01-24', '2020-01-24', 'city', '+71001001010', 'user@mail.com', 'active')"
    })
    public void testGetUser() {
        Optional<UserDo> userDoOptional = userDao.getUser(1L);
        assertThat(userDoOptional).isNotEmpty();

        UserDo userDo = userDoOptional.get();
        assertThat(userDo.getId()).isEqualTo(1L);
        assertThat(userDo.getFirstName()).isEqualTo("firstName");
        assertThat(userDo.getPatronymicName()).isEqualTo("patronymic");
        assertThat(userDo.getLastName()).isEqualTo("lastName");
        assertThat(userDo.getDateOfBirth()).isEqualTo(Date.valueOf(LocalDate.of(1990, 1, 24)));
        assertThat(userDo.getDateOfRegistration()).isEqualTo(Date.valueOf(LocalDate.of(2020, 1, 24)));
        assertThat(userDo.getCity()).isEqualTo("city");
        assertThat(userDo.getMobilePhone()).isEqualTo("+71001001010");
        assertThat(userDo.getEmail()).isEqualTo("user@mail.com");
        assertThat(userDo.getStatus()).isEqualTo("active");
    }

    @Test
    public void testGetUser_NotFound() {
        Optional<UserDo> userDoOptional = userDao.getUser(1L);
        assertThat(userDoOptional).isEmpty();
    }

    @Test
    public void testCreateUser() {
        assertThat(getRowCount()).isEqualTo(0);

        userDao.createUser(MockObjects.userDo());

        assertThat(getRowCount()).isEqualTo(1);
    }

    @Test
    @Sql(statements = {
        "INSERT INTO users (id, first_name, last_name, patronymic_name, " +
        "date_of_birth, date_of_registration, city, mobile_phone, email, status) " +
        "VALUES (1, 'value', 'value', 'value', " +
        "'1990-01-01', '2020-01-01', 'value', 'value', 'value', 'value')"
    })
    public void testUpdateUser() {
        assertThat(getRowCount()).isEqualTo(1);

        userDao.updateUser(MockObjects.userDo());

        assertThat(getRowCount()).isEqualTo(1);

        UserDo userDo = userDao.getUser(1L).orElse(null);
        assertThat(userDo).isNotNull();
        assertThat(userDo.getId()).isEqualTo(1L);
        assertThat(userDo.getFirstName()).isEqualTo("firstName");
        assertThat(userDo.getPatronymicName()).isEqualTo("patronymic");
        assertThat(userDo.getLastName()).isEqualTo("lastName");
        assertThat(userDo.getDateOfBirth()).isEqualTo(Date.valueOf(LocalDate.of(1990, 1, 24)));
        assertThat(userDo.getDateOfRegistration()).isEqualTo(Date.valueOf(LocalDate.of(2020, 1, 24)));
        assertThat(userDo.getCity()).isEqualTo("city");
        assertThat(userDo.getMobilePhone()).isEqualTo("+71001001010");
        assertThat(userDo.getEmail()).isEqualTo("user@mail.com");
        assertThat(userDo.getStatus()).isEqualTo("active");
    }

    @Test
    @Sql(statements = {
        "INSERT INTO users (id, first_name, last_name, patronymic_name, " +
        "date_of_birth, date_of_registration, city, mobile_phone, email, status) " +
        "VALUES (1, 'firstName', 'lastName', 'patronymic', " +
        "'1990-01-24', '2020-01-24', 'city', '+71001001010', 'user@mail.com', 'active')"
    })
    public void testDeleteUser() {
        assertThat(getRowCount()).isEqualTo(1);

        assertThat(userDao.deleteUser(1L)).isTrue();

        assertThat(getRowCount()).isEqualTo(0);
    }

    @Test
    public void testDeleteUser_NotFound() {
        assertThat(getRowCount()).isEqualTo(0);

        assertThat(userDao.deleteUser(1L)).isFalse();

        assertThat(getRowCount()).isEqualTo(0);
    }

    private Integer getRowCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM users", Collections.emptyMap(), Integer.class);
    }

}