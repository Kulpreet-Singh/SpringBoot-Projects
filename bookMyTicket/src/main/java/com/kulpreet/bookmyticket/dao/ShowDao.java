package com.kulpreet.bookmyticket.dao;

import com.kulpreet.bookmyticket.dto.ShowInfo;
import com.kulpreet.bookmyticket.model.Show;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowDao {
    Long save(Show show);

    Show findById(Long showId);

    Long countById(Long id);

    List<ShowInfo> findShowInfo(boolean running, Long cityId, Long theaterId, Long movieId, Long showId);

    Long countOverlappingShows(Long hallId, LocalDateTime startTime, LocalDateTime endTime);

}
