package com.airportSystem.airport_system.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {

    private Long flightId;
    private String seatNumber;

}