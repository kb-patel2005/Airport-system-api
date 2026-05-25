package com.airportSystem.airport_system.Service;
import com.airportSystem.airport_system.Entities.Booking;
import com.airportSystem.airport_system.Entities.FlightDto;
import com.airportSystem.airport_system.Entities.Flights;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightService {

    Page<FlightDto> getAllFlights(Pageable pageable);
    Optional<Flights> getFlightById(Long id);
    String addFlight(Flights flight);
    String updateFlight(Flights flight);
    String deleteFlight(Long id);
    List<List<Boolean>> getSeatsByClass(Long id);
    Flights saveFlight(Flights flight);
    void cancelFlightBooking(List<Booking> seats);
    public List<FlightDto> FlightByOriginAndDestination(String oCountry,
            String oState,
            String ocity,
            String dCountry,
            String dState,
            String dCity);
}
