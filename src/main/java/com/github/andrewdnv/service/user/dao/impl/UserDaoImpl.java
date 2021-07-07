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

    private static final String INSERT_SQL = "INSERT INTO user (first_name, last_name, patronymic_name, " +
        "date_of_birth, date_of_registration, city, mobile_phone, email, status) " +
        "VALUES (:firstName, :lastName, :patronymicName, " +
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
        List<UserDo> users = jdbcTemplate.query(SELECT_SQL, sqlParams, (rs, i) -> {
            UserDo userDo = new UserDo();
            userDo.setId(rs.getLong("id"));
            userDo.setFirstName(rs.getString("first_name"));
            userDo.setLastName(rs.getString("last_name"));
            userDo.setPatronymicName(rs.getString("patronymic_name"));
            userDo.setDateOfBirth(rs.getDate("date_of_birth"));
            userDo.setDateOfRegistration(rs.getDate("date_of_registration"));
            userDo.setCity(rs.getString("city"));
            userDo.setMobilePhone(rs.getString("mobile_phone"));
            userDo.setEmail(rs.getString("email"));
            userDo.setStatus(rs.getString("status"));
            return userDo;
        });
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }

    @Override
    public Optional<UserDo> createUser(UserDo userDo) {
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
        int result;
        if (userDo.getId() != null) {
            sqlParams.addValue("id", userDo.getId());
            result = jdbcTemplate.update(INSERT_SQL, sqlParams);
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            result = jdbcTemplate.update(INSERT_SQL, sqlParams, keyHolder, new String[]{"id"});
            Long id = (Long) keyHolder.getKey();
            userDo.setId(id);
        }
        return result == 1 ? Optional.of(userDo) : Optional.empty();
    }

    @Override
    public Optional<UserDo> updateUser(UserDo userDo) {
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
        int result = jdbcTemplate.update(UPDATE_SQL, sqlParams);
        return result == 1 ? Optional.of(userDo) : Optional.empty();
    }

    @Override
    public boolean deleteUser(Long id) {
        Map<String, Object> sqlParams = Collections.singletonMap("id", id);
        return jdbcTemplate.update(DELETE_SQL, sqlParams) == 1;
    }

}