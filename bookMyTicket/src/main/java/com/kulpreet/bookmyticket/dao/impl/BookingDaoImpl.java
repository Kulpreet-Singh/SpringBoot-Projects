package com.kulpreet.bookmyticket.dao.impl;

import com.kulpreet.bookmyticket.dao.BookingDao;
import com.kulpreet.bookmyticket.dto.BookingInfo;
import com.kulpreet.bookmyticket.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class BookingDaoImpl implements BookingDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long save(Booking booking) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", booking.getUserId())
                .addValue("bookingTime", booking.getBookingTime());
        namedParameterJdbcTemplate.update("INSERT INTO bookings (user_id, booking_time) VALUES (:userId, :bookingTime)", namedParameters, keyHolder);
        booking.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<BookingInfo> findByUserId(boolean upcoming, Long userId) {
        String sql = "SELECT bs.booking_id, u.username, b.booking_time, group_concat(concat(s.`row`, '-', s.number)) as seat_number, bs.show_id \n" +
                "FROM users u join bookings b on u.id = b.user_id join booked_seats bs on b.id = bs.booking_id \n" +
                "join seats s on bs.seat_id = s.id join shows sh on bs.show_id = sh.id \n" +
                "WHERE u.id = :userId ";
        if (upcoming) {
            sql += "and sh.show_time > now() ";
        }
        sql += "group by bs.booking_id, sh.id ";
        if (upcoming) {
            sql += "order by sh.show_time asc";
        } else {
            sql += "order by sh.show_time desc";
        }
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource("userId", userId), new BeanPropertyRowMapper<>(BookingInfo.class));
    }
}
