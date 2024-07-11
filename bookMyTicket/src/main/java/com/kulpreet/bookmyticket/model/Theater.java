package com.kulpreet.bookmyticket.model;

import com.kulpreet.bookmyticket.dto.CreateTheaterDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Theater {
    private Long id;
    private Long cityId;
    private String name;

    public Theater(CreateTheaterDto createTheaterDto) {
        this.cityId = createTheaterDto.getCityId();
        this.name = createTheaterDto.getName();
    }

}

