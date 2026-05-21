package com.airportSystem.airport_system.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Entities.SeatKey;

public interface SeatRepository extends JpaRepository<Seat, SeatKey> {

    // Query by the Flights entity directly
    List<Seat> findByFlight(Flights flight);

    // Query by Flights entity + seat number
    Optional<Seat> findByFlightAndIdSeatNumber(Flights flight, String seatNumber);

    // Alternatively, if you want to query by raw flightId instead of entity:
    List<Seat> findByIdFlightId(Long flightId);

    Optional<Seat> findByIdFlightIdAndIdSeatNumber(Long flightId, String seatNumber);
}

