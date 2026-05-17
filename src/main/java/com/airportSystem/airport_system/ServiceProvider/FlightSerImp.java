package com.airportSystem.airport_system.ServiceProvider;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Dao.SeatRepository;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Service.FlightService;

@Service
public class FlightSerImp implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

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
    public Flights saveFlight(Flights flight) {
        return flightRepository.save(flight);
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
        Optional<Seat> optionalSeat = seatRepository.findById(seat.getId());
        if (optionalSeat.isPresent()) {
            Seat existingSeat = optionalSeat.get();
            existingSeat.setBooked(false);
            existingSeat.setPassenger(null);
            seatRepository.save(existingSeat);
            return "Flight cancelled successfully";
        } else {
            return "Seat not found";
        }
    }

    @Override
    public List<List<Boolean>> getSeatsByClass(int id, String className) {
        Optional<Flights> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()) {
            Flights flight = optionalFlight.get();
            List<List<Boolean>> seatsByClass = new ArrayList<>();
            for (Seat seat : flight.getSeats()) {
                if (seat.getSeatClass().equals(className)) {
                    if (seatsByClass.size() <= Integer.parseInt(seat.getRowNumber())) {
                        seatsByClass.add(new ArrayList<>());
                    }
                    seatsByClass.get(Integer.parseInt(seat.getRowNumber()) - 1).add(seat.isBooked());
                }
            }
            return seatsByClass;
        } else {
            throw new IllegalArgumentException("Flight not found");
        }
    }

}