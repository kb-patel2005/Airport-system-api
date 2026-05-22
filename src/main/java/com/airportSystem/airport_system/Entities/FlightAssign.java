package com.airportSystem.airport_system.Entities;
import java.util.List;

import lombok.Data;

@Data
public class FlightAssign {

    private Long passengerId;
    private Flights flight;
    private List<Seat> seats;
    
}
