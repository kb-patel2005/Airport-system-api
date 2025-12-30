package com.airportSystem.airport_system.ServiceProvider;
import com.airportSystem.airport_system.Dao.Flights;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface FlightService {

    org.springframework.data.domain.Page<Flights> getAllFlights(Pageable pageable);

    Optional<Flights> getFlightById(int id);

    String addFlight(Flights flight);
    String updateFlight(Flights flight);
    String deleteFlight(int id);

    // List<Flights> findByOriginAndDestination(String origin, String destination);
    
}
