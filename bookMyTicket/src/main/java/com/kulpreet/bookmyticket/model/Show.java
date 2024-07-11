package com.kulpreet.bookmyticket.model;

import com.kulpreet.bookmyticket.dto.CreateShowDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class Show {
    private Long id;
    private Long hallId;
    private Long movieId;
    private LocalDateTime showTime;
    private LocalDateTime endTime;

    public Show(CreateShowDto createShowDto) {
        this.hallId = createShowDto.getHallId();
        this.movieId = createShowDto.getMovieId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.showTime = LocalDateTime.parse(createShowDto.getShowTime(), formatter);
    }
}
