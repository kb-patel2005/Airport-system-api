package com.airportSystem.airport_system.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisplaySeats {
    private Long flightId;
    private String from;
    private String to;
    private double price;
    private String SeatNumber;
}
