package com.airportSystem.airport_system.Dao;
import lombok.Data;

@Data
public class FlightAssign {

    private int passengerId;
    private Flights flight;
    private String seatNo;
    
}
