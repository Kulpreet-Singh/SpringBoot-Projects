package com.kulpreet.bookmyticket.dao.impl;

import com.kulpreet.bookmyticket.dao.UserDao;
import com.kulpreet.bookmyticket.exception.SystemException;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_USER;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User findById(Long id) {
        try {
            return namedParameterJdbcTemplate.queryForObject("SELECT * FROM users WHERE id = :id", Map.of("id", id), new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            log.error("User with id {} not found", id);
            throw new ValidationException(NO_SUCH_USER.getCode(), NO_SUCH_USER.getMessage() + id);
        } catch (Exception e) {
            log.error("Error while fetching user with id {}", id, e);
            throw new SystemException("Error while fetching user");
        }
    }

    @Override
    public Long countById(Long id) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM users WHERE id = :id", Map.of("id", id), Long.class);
    }

    @Override
    public List<User> findByUsernameOrEmailOrMobile(User user) {
        return namedParameterJdbcTemplate.query("SELECT * FROM users WHERE username = :username OR email = :email OR mobile = :mobile",
                Map.of("username", user.getUsername(), "email", user.getEmail(), "mobile", user.getMobile()), new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Long countByUsernameOrEmailOrMobile(User user) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM users WHERE username = :username OR email = :email OR mobile = :mobile",
                Map.of("username", user.getUsername(), "email", user.getEmail(), "mobile", user.getMobile()), Long.class);
    }

    @Override
    public Long countByUsernameOrEmailOrMobileAndNotId(User user) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM users WHERE (username = :username OR email = :email OR mobile = :mobile) AND id != :id",
                Map.of("username", user.getUsername(), "email", user.getEmail(), "mobile", user.getMobile(), "id", user.getId()), Long.class);
    }

    @Override
    public Long save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("email", user.getEmail())
                .addValue("mobile", user.getMobile())
                .addValue("gender", user.getGender())
                .addValue("age", user.getAge());
        namedParameterJdbcTemplate.update("INSERT INTO users (username, email, mobile, gender, age) VALUES (:username, :email, :mobile, :gender, :age)", namedParameters, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(User user) {
        return namedParameterJdbcTemplate.update("UPDATE users SET username = :username, email = :email, mobile = :mobile, gender = :gender, age = :age WHERE id = :id",
                new MapSqlParameterSource("username", user.getUsername()).addValue("email", user.getEmail()).addValue("mobile", user.getMobile())
                        .addValue("gender", user.getGender()).addValue("age", user.getAge()).addValue("id", user.getId()));
    }
}
