package com.kulpreet.bookmyticket.model;

import com.kulpreet.bookmyticket.dto.CreateCityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class City {
    private Long id;
    private String name;
    private String state;

    public City(CreateCityDto createCityDto) {
        this.name = createCityDto.getName();
        this.state = createCityDto.getState();
    }

}
