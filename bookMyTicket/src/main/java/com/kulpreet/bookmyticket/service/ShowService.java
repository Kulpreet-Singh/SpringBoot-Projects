package com.kulpreet.bookmyticket.service;

import com.kulpreet.bookmyticket.dto.ShowInfo;
import com.kulpreet.bookmyticket.model.Show;

import java.util.List;

public interface ShowService {
    Long hostShow(Show show);

    Show findById(Long showId);

    List<ShowInfo> findShowInfo(boolean running, Long cityId, Long theaterId, Long movieId, Long showId);
}
