package com.kulpreet.bookmyticket.dao.impl;

import com.kulpreet.bookmyticket.dao.BookedSeatDao;
import com.kulpreet.bookmyticket.model.BookedSeat;
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
import java.util.Map;

@Repository
public class BookedSeatDaoImpl implements BookedSeatDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<BookedSeat> findByShowId(Long showId) {
        return namedParameterJdbcTemplate.query("SELECT * FROM booked_seats WHERE show_id = :showId", Map.of("showId", showId), new BeanPropertyRowMapper<>(BookedSeat.class));
    }

    @Override
    public List<BookedSeat> findByBookingId(Long bookingId) {
        return namedParameterJdbcTemplate.query("SELECT * FROM booked_seats WHERE booking_id = :bookingId", Map.of("bookingId", bookingId), new BeanPropertyRowMapper<>(BookedSeat.class));
    }

    @Override
    public Long save(BookedSeat bookedSeat) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bookingId", bookedSeat.getBookingId())
                .addValue("showId", bookedSeat.getShowId())
                .addValue("seatId", bookedSeat.getSeatId());
        namedParameterJdbcTemplate.update("INSERT INTO booked_seats (booking_id, show_id, seat_id) VALUES (:bookingId, :showId, :seatId)", namedParameters, keyHolder);
        bookedSeat.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }
}