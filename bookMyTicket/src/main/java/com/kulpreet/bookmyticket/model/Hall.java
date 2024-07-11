package com.kulpreet.bookmyticket.model;

import com.kulpreet.bookmyticket.dto.CreateHallDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Hall {
    private Long id;
    private Long theaterId;
    private String name;
    private int capacityRow;
    private int capacityColumn;

    public Hall(CreateHallDto createHallDto) {
        this.theaterId = createHallDto.getTheaterId();
        this.name = createHallDto.getName();
        this.capacityRow = createHallDto.getCapacityRow();
        this.capacityColumn = createHallDto.getCapacityColumn();
    }
}
