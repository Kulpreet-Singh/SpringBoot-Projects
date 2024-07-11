package com.kulpreet.bookmyticket.dao.impl;

import com.kulpreet.bookmyticket.dao.SeatDao;
import com.kulpreet.bookmyticket.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatDaoImpl implements SeatDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long save(Seat seat) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("hallId", seat.getHallId())
                .addValue("row", String.valueOf(seat.getRow()))
                .addValue("number", seat.getNumber());
        namedParameterJdbcTemplate.update("INSERT INTO seats (hall_id, `row`, number) VALUES (:hallId, :row, :number)", namedParameters, keyHolder);
        seat.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Seat> findByShowId(Long showId) {
        String sql = "SELECT s.* FROM seats s " +
                "JOIN halls h ON s.hall_id = h.id " +
                "JOIN shows sh ON h.id = sh.hall_id " +
                "WHERE sh.id = :showId";
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource("showId", showId), new BeanPropertyRowMapper<>(Seat.class));
    }
}

