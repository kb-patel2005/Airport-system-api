package com.airportSystem.airport_system.Dao;

import java.util.List;
import java.util.Optional;

import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Seat;

public interface SeatRepository extends org.springframework.data.jpa.repository.JpaRepository<com.airportSystem.airport_system.Entities.Seat, com.airportSystem.airport_system.Entities.SeatKey> {
    
    List<Seat> findById_Flight(Flights flight);

    Optional<Seat> findById_FlightAndId_SeatNumber(Flights flight, String seatNumber);
}
