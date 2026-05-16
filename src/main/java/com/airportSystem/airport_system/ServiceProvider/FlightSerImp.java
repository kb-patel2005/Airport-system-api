package com.airportSystem.airport_system.ServiceProvider;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Dao.Repository;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Passenger;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Service.AirportService;
import com.airportSystem.airport_system.Service.FlightService;

@Service
public class FlightSerImp implements FlightService{

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private Repository repository;

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
        flightRepository.deleteById(id);
        return "Flight cancelled successfully";
    }

    @Override
    public String cancelFlight(Seat seat) {
        Flights flight = flightRepository.findById(Integer.parseInt(seat.getFlightId())).orElse(null);
        if (flight != null) {
            Boolean[][] businessClass = flight.getBusinessClass();
            Boolean[][] economyClass = flight.getEconomyClass();
            String[] seatInfo = seat.getSeatNumber().split("-");
            int row = Integer.parseInt(seatInfo[0]);
            int column = Integer.parseInt(seatInfo[1]);
            if (seat.getSeatClass().equalsIgnoreCase("business")) {
                if (row >= 0 && row < businessClass.length && column >= 0 && column < businessClass[row].length) {
                    businessClass[row][column] = false; 
                }
            } else {
                if (row >= 0 && row < economyClass.length && column >= 0 && column < economyClass[row].length) {
                    economyClass[row][column] = false; 
                }
            }
            flightRepository.save(flight); 
            Passenger passenger = seat.getPassenger();
            if (passenger != null) {
                passenger.getSeats().remove(seat);
            }
            repository.save(passenger);
            return "Seat cancelled successfully";
        }
        throw new UnsupportedOperationException("Unimplemented method 'cancelFlight'");
    }

}