package com.kulpreet.bookmyticket.dao.impl;

import com.kulpreet.bookmyticket.dao.TheaterDao;
import com.kulpreet.bookmyticket.model.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TheaterDaoImpl implements TheaterDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long save(Theater theater) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("cityId", theater.getCityId())
                .addValue("name", theater.getName());
        namedParameterJdbcTemplate.update("INSERT INTO theaters (city_id, name) VALUES (:cityId, :name)", namedParameters, keyHolder);
        theater.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Theater> findAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM theaters", new BeanPropertyRowMapper<>(Theater.class));
    }

    @Override
    public List<Theater> findByCityId(Long cityId) {
        return namedParameterJdbcTemplate.query("SELECT * FROM theaters WHERE city_id = :cityId", new MapSqlParameterSource("cityId", cityId), new BeanPropertyRowMapper<>(Theater.class));
    }

    @Override
    public Long countById(Long id) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM theaters WHERE id = :id", new MapSqlParameterSource("id", id), Long.class);
    }

    @Override
    public Long countByCityIdAndName(Long cityId, String name) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM theaters WHERE city_id = :cityId AND name = :name", new MapSqlParameterSource("cityId", cityId).addValue("name", name), Long.class);
    }
}


