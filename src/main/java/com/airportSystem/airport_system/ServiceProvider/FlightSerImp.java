package com.airportSystem.airport_system.ServiceProvider;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Dao.Flights;

@Service
public class FlightSerImp implements FlightService{

    @Autowired
    FlightRepository flightRepository;

    @Override
    public Page<Flights> getAllFlights(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }

    @Override
    public Optional<Flights> getFlightById(int id) {
        return flightRepository.findById(id);
    }

    @Override
    public String addFlight(Flights flight) {
        flightRepository.save(flight);
        return "Flight added successfully";
    }

    @Override
    public String updateFlight(Flights flight) {
        flightRepository.save(flight);
        return "Flight updated successfully";
    }

    @Override
    public String deleteFlight(int id) {
        return "Flight cancelled successfully";
    }

    // @Override
    // public List<Flights> findByOriginAndDestination(String origin, String destination) {
    //     return flightRepository.findByOriginAndDestination(origin, destination);
    // }

}
