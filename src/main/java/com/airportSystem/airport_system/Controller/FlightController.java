package com.airportSystem.airport_system.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.airportSystem.airport_system.Dao.SeatRepository;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Entities.SeatKey;
import com.airportSystem.airport_system.Service.FlightService;

@RestController
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private SeatRepository seatRepository;

    @PostMapping("/addFlight")
    public String addFlight(@RequestBody Flights flight) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!role.contains("ADMIN")) {
            return "Unauthorized";
        }
        Flights savedFlight = flightService.saveFlight(flight);
        char[] columns = { 'A', 'B', 'C', 'D', 'E', 'F' };

        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= 15; row++) {
            for (char col : columns) {
                SeatKey seatKey = new SeatKey();
                seatKey.setFlightId(savedFlight.getId());
                seatKey.setSeatNumber(String.valueOf(col) + row);
                Seat seat = new Seat();
                seat.setId(seatKey);
                seat.setFlight(savedFlight);
                seat.setBooked(false);
                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
        savedFlight.setSeats(seats);
        return flightService.addFlight(savedFlight);
    }

    @DeleteMapping("/deleteFlight/{id}")
    public String deleteFlight(@PathVariable("id") Long id) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!role.contains("ADMIN")) {
            return "Unauthorized";
        }
        return flightService.deleteFlight(id);
    }

    

}
