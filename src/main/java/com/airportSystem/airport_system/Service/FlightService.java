package com.airportSystem.airport_system.Service;
import com.airportSystem.airport_system.Entities.FlightDto;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Seat;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightService {

    Page<FlightDto> getAllFlights(Pageable pageable);
    Optional<Flights> getFlightById(int id);
    String addFlight(Flights flight);
    String updateFlight(Flights flight);
    String deleteFlight(int id);
    String cancelFlight(Seat seat);
    List<List<Boolean>> getSeatsByClass(int id, String className);
    Flights saveFlight(Flights flight);

}
