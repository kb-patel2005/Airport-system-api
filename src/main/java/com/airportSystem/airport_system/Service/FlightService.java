package com.airportSystem.airport_system.Service;
import com.airportSystem.airport_system.Entities.BusinessSeats;
import com.airportSystem.airport_system.Entities.EconomicSeats;
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
    String cancelEconomicFlight(EconomicSeats seat);
    String cancelBusinessFlight(BusinessSeats seat);
    List<List<Boolean>> getSeatsByClass(Long id, String className);
    Flights saveFlight(Flights flight);

}
