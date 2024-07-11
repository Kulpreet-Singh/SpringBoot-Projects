package com.kulpreet.bookmyticket.dao.impl;

import com.kulpreet.bookmyticket.dao.ShowDao;
import com.kulpreet.bookmyticket.dto.ShowInfo;
import com.kulpreet.bookmyticket.exception.SystemException;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.Show;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_SHOW;

@Slf4j
@Repository
public class ShowDaoImpl implements ShowDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Long save(Show show) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("hallId", show.getHallId())
                .addValue("movieId", show.getMovieId())
                .addValue("showTime", show.getShowTime())
                .addValue("endTime", show.getEndTime());
        namedParameterJdbcTemplate.update("INSERT INTO shows (hall_id, movie_id, show_time, end_time) VALUES (:hallId, :movieId, :showTime, :endTime)", namedParameters, keyHolder);
        show.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }

    @Override
    public Show findById(Long showId) {
        String sql = "SELECT * FROM shows WHERE id = :showId";
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, Map.of("showId", showId), new BeanPropertyRowMapper<>(Show.class));
        } catch (EmptyResultDataAccessException e) {
            log.error("Show with id {} not found", showId);
            throw new ValidationException(NO_SUCH_SHOW.getCode(), NO_SUCH_SHOW.getMessage() + showId);
        } catch (Exception e) {
            log.error("Error while fetching show with id {}", showId, e);
            throw new SystemException("Error while fetching show");
        }
    }

    @Override
    public Long countById(Long id) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM shows WHERE id = :id", Map.of("id", id), Long.class);
    }

    @Override
    public List<ShowInfo> findShowInfo(boolean running, Long cityId, Long theaterId, Long movieId, Long showId) {
        String sql = "SELECT sh.id as show_id, m.title as movie_name, sh.show_time, t.name as theater_name, c.name as city, h.name as hall_name " +
                "FROM shows sh JOIN movies m ON sh.movie_id = m.id JOIN halls h ON sh.hall_id = h.id " +
                "JOIN theaters t ON h.theater_id = t.id JOIN cities c ON t.city_id = c.id ";
        List<String> conditions = new ArrayList<>(Arrays.asList(
                running ? "sh.show_time > NOW()" : null,
                cityId != null ? "c.id = :cityId" : null,
                theaterId != null ? "t.id = :theaterId" : null,
                movieId != null ? "m.id = :movieId" : null,
                showId != null ? "sh.id = :showId" : null
        ));
        conditions.removeIf(condition -> condition == null);
        if(!conditions.isEmpty()) {
            sql += "WHERE ";
            sql += String.join(" AND ", conditions);
        }
        if (running) {
            sql += " order by sh.show_time asc";
        } else {
            sql += " order by sh.show_time desc";
        }
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        if (cityId != null) {
            namedParameters.addValue("cityId", cityId);
        }
        if (theaterId != null) {
            namedParameters.addValue("theaterId", theaterId);
        }
        if (movieId != null) {
            namedParameters.addValue("movieId", movieId);
        }
        if (showId != null) {
            namedParameters.addValue("showId", showId);
        }
        return namedParameterJdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ShowInfo.class));
    }

    @Override
    public Long countOverlappingShows(Long hallId, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "SELECT COUNT(1) FROM shows WHERE hall_id = :hallId AND ((:startTime BETWEEN show_time AND end_time) OR (:endTime BETWEEN show_time AND end_time) OR (show_time BETWEEN :startTime AND :endTime))";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("hallId", hallId)
                .addValue("startTime", startTime)
                .addValue("endTime", endTime);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Long.class);
    }


}

