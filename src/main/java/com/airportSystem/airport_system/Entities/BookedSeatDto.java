package com.airportSystem.airport_system.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedSeatDto {
    private Long id;

    private String seatNumber;

    private Double seatPrice;

    private String status;
}
