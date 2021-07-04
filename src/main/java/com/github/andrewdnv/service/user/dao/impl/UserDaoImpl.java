package com.github.andrewdnv.service.user.dao.impl;

import com.github.andrewdnv.service.user.dao.api.UserDao;
import com.github.andrewdnv.service.user.entity.UserDo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private static final String SELECT_SQL = "SELECT id, first_name, last_name, patronymic_name, " +
        "date_of_birth, date_of_registration, city, mobile_phone, email, status " +
        "FROM user WHERE id = :id";

    private static final String INSERT_SQL = "INSERT INTO user (id, first_name, last_name, patronymic_name, " +
        "date_of_birth, date_of_registration, city, mobile_phone, email, status) " +
        "VALUES (:id, :firstName, :lastName, :patronymicName, " +
        ":dateOfBirth, :dateOfRegistration, :city, :mobilePhone, :email, :status)";

    private static final String UPDATE_SQL = "UPDATE user " +
        "SET first_name = :firstName, last_name = :lastName, patronymic_name = :patronymicName, " +
        "date_of_birth = :dateOfBirth, date_of_registration = :dateOfRegistration, " +
        "city = :city, mobile_phone = :mobilePhone, email = :email, status = :status " +
        "WHERE id = :id";

    private static final String DELETE_SQL = "DELETE from user WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<UserDo> getUser(Long id) {
        Map<String, Object> sqlParams = Collections.singletonMap("id", id);
        List<UserDo> users = jdbcTemplate.queryForList(SELECT_SQL, sqlParams, UserDo.class);
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }

    @Override
    public UserDo createUser(UserDo userDo) {
        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
            .addValue("firstName", userDo.getFirstName())
            .addValue("lastName", userDo.getLastName())
            .addValue("patronymicName", userDo.getPatronymicName())
            .addValue("dateOfBirth", userDo.getDateOfBirth())
            .addValue("dateOfRegistration", userDo.getDateOfRegistration())
            .addValue("city", userDo.getCity())
            .addValue("mobilePhone", userDo.getMobilePhone())
            .addValue("email", userDo.getEmail())
            .addValue("status", userDo.getStatus());
        if (userDo.getId() != null) {
            sqlParams.addValue("id", userDo.getId());
            jdbcTemplate.update(INSERT_SQL, sqlParams);
            return userDo;
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(INSERT_SQL, sqlParams, keyHolder, new String[]{"id"});
            Long id = (Long) keyHolder.getKey();
            return userDo.setId(id);
        }
    }

    @Override
    public UserDo updateUser(UserDo userDo) {
        SqlParameterSource sqlParams = new MapSqlParameterSource()
            .addValue("id", userDo.getId())
            .addValue("firstName", userDo.getFirstName())
            .addValue("lastName", userDo.getLastName())
            .addValue("patronymicName", userDo.getPatronymicName())
            .addValue("dateOfBirth", userDo.getDateOfBirth())
            .addValue("dateOfRegistration", userDo.getDateOfRegistration())
            .addValue("city", userDo.getCity())
            .addValue("mobilePhone", userDo.getMobilePhone())
            .addValue("email", userDo.getEmail())
            .addValue("status", userDo.getStatus());
        jdbcTemplate.update(UPDATE_SQL, sqlParams);
        return userDo;
    }

    @Override
    public boolean deleteUser(Long id) {
        Map<String, Object> sqlParams = Collections.singletonMap("id", id);
        return jdbcTemplate.update(DELETE_SQL, sqlParams) == 1;
    }

}