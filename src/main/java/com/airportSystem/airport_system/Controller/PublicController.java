package com.airportSystem.airport_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airportSystem.airport_system.Entities.FlightDto;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Service.FlightService;

@RestController
@CrossOrigin("*")
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/flight/{id}")
    public Flights getflightById(@PathVariable("id") Long id) {
        return flightService.getFlightById(id).orElse(null);
    }

    @GetMapping("/allFlights")
    public Page<FlightDto> getAllFlights(Pageable pageable) {
        return flightService.getAllFlights(pageable);
    }

    @GetMapping("/{id}/seats")
    public List<List<Boolean>> getSeatsByClass(@PathVariable("id") Long id) {
        return flightService.getSeatsByClass(id);
    }

    @GetMapping("/flightUsingLocation")
    public List<FlightDto> returnFlightByOriginAndDestination(@RequestParam("originCountry") String oCountry,
            @RequestParam("originState") String oState,
            @RequestParam("originCity") String ocity,
            @RequestParam("destinationCountry") String dCountry,
            @RequestParam("destinationState") String dState,
            @RequestParam("destinationCity") String dCity) {
        return flightService.FlightByOriginAndDestination(oCountry, oState, ocity, dCountry, dState, dCity);
    }

}
