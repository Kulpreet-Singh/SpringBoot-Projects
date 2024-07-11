package com.kulpreet.bookmyticket.dao.impl;

import com.kulpreet.bookmyticket.dao.CityDao;
import com.kulpreet.bookmyticket.exception.SystemException;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_CITY;

@Slf4j
@Repository
public class CityDaoImpl implements CityDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<City> findAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM cities", new BeanPropertyRowMapper<>(City.class));
    }

    public City findById(Long id) {
        try {
            return namedParameterJdbcTemplate.queryForObject("SELECT * FROM cities WHERE id = :id", Map.of("id", id), new BeanPropertyRowMapper<>(City.class));
        } catch (EmptyResultDataAccessException e) {
            log.error("City with id {} not found", id);
            throw new ValidationException(NO_SUCH_CITY.getCode(), NO_SUCH_CITY.getMessage() + id);
        } catch (Exception e) {
            log.error("Error while fetching city with id {}", id, e);
            throw new SystemException("Error while fetching city");
        }
    }

    @Override
    public Long countById(Long id) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM cities WHERE id = :id", Map.of("id", id), Long.class);
    }

    @Override
    public Long countByName(String name) {
        return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(1) FROM cities WHERE name = :name", Map.of("name", name), Long.class);
    }

    public Long save(City city) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", city.getName())
                .addValue("state", city.getState());
        namedParameterJdbcTemplate.update("INSERT INTO cities (name, state) VALUES (:name, :state)", namedParameters, keyHolder);
        city.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }
}
