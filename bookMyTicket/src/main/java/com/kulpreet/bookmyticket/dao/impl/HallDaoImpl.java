package com.kulpreet.bookmyticket.dao.impl;

import com.kulpreet.bookmyticket.dao.HallDao;
import com.kulpreet.bookmyticket.model.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class HallDaoImpl implements HallDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long save(Hall hall) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("theaterId", hall.getTheaterId())
                .addValue("name", hall.getName())
                .addValue("capacityRow", hall.getCapacityRow())
                .addValue("capacityColumn", hall.getCapacityColumn());
        namedParameterJdbcTemplate.update("INSERT INTO halls (theater_id, name, capacity_row, capacity_column) " +
                "VALUES (:theaterId, :name, :capacityRow, :capacityColumn)", namedParameters, keyHolder);
        hall.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Hall> findByTheaterId(Long theaterId) {
        String sql = "SELECT * FROM halls WHERE theater_id = :theaterId";
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource("theaterId", theaterId), new BeanPropertyRowMapper<>(Hall.class));
    }

    @Override
    public Long countById(Long id) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM halls WHERE id = :id", new MapSqlParameterSource("id", id), Long.class);
    }

    @Override
    public Long countByTheaterIdAndName(Long theaterId, String name) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM halls WHERE theater_id = :theaterId AND name = :name", new MapSqlParameterSource("theaterId", theaterId).addValue("name", name), Long.class);
    }


}
