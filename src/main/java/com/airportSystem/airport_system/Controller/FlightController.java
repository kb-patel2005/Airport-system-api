package com.airportSystem.airport_system.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.airportSystem.airport_system.Dao.SeatRepository;
import com.airportSystem.airport_system.Entities.FlightDto;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Entities.SeatKey;
import com.airportSystem.airport_system.Service.FlightService;

@RestController
@CrossOrigin("*")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private SeatRepository seatRepository;

    @GetMapping("/flight/{id}")
    public Flights getflightById(@PathVariable("id") Long id) {
        return flightService.getFlightById(id).orElse(null);
    }

    @GetMapping("/allFlights")
    public Page<FlightDto> getAllFlights(Pageable pageable) {
        return flightService.getAllFlights(pageable);
    }

    @PostMapping("/addFlight")
    public String addFlight(@RequestBody Flights flight) {
        Flights savedFlight = flightService.saveFlight(flight);
        char[] columns = { 'A', 'B', 'C', 'D', 'E', 'F' };

        savedFlight.setSeats(new ArrayList<>());

        for (int row = 1; row <= 15; row++) { // rows 1–15
            for (char col : columns) { // columns A–F
                SeatKey seatKey = new SeatKey();
                seatKey.setFlightId(savedFlight.getId());
                seatKey.setSeatNumber(col + String.valueOf(row));
                Seat seat = new Seat();
                seat.setId(seatKey);
                seat.setFlight(savedFlight);
                seat.setBooked(false);
                seat.setPassenger(null);
                savedFlight.getSeats().add(seatRepository.save(seat));
            }
        }
        return flightService.addFlight(flight);
    }

    @DeleteMapping("/deleteFlight/{id}")
    public String deleteFlight(@PathVariable("id") Long id) {
        return flightService.deleteFlight(id);
    }

    @GetMapping("/{id}/seats")
    public List<List<Boolean>> getSeatsByClass(@PathVariable("id") Long id) {
        return flightService.getSeatsByClass(id);
    }
}
