package com.airportSystem.airport_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.airportSystem.airport_system.Entities.FlightDto;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Service.FlightService;

@RestController
@CrossOrigin("*")
public class FlightController {

    @Autowired
    FlightService flightService;

    @GetMapping("/flight/{id}")
    public Flights getflightById(@PathVariable("id") int id) {
       return flightService.getFlightById(id).orElse(null);
    }

    @GetMapping("/allFlights")
    public Page<FlightDto> getAllFlights(Pageable pageable) {
        return flightService.getAllFlights(pageable);
    }

    @PostMapping("/addFlight")
    public String addFlight(@RequestBody Flights flight){
        flight.setSeats(new java.util.ArrayList<>());
        Flights savedFlight = flightService.saveFlight(flight);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Seat seat = new Seat();
                seat.setBooked(false);
                seat.setRowNumber(String.valueOf(i + 1));
                seat.setColNumber(String.valueOf(j + 1));
                seat.setSeatClass("Bussiness");
                seat.setFlight(savedFlight);
                savedFlight.getSeats().add(seat);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                Seat seat = new Seat();
                seat.setBooked(false);
                seat.setRowNumber(String.valueOf(i + 1));
                seat.setColNumber(String.valueOf(j + 1));
                seat.setSeatClass("Economic");
                seat.setFlight(savedFlight);
                savedFlight.getSeats().add(seat);
            }
        }
        return flightService.addFlight(savedFlight);
    }

    @DeleteMapping("/deleteFlight/{id}")
    public String deleteFlight(@PathVariable("id") int id) {
        return flightService.deleteFlight(id);
    }

    @GetMapping("/{id}/seats/{className}")
    public List<List<Boolean>> getSeatsByClass(@PathVariable("id") int id, @PathVariable("className") String className) {
        return flightService.getSeatsByClass(id, className);
    }
}
