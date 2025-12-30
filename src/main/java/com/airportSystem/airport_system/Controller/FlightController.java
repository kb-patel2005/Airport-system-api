package com.airportSystem.airport_system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.airportSystem.airport_system.Dao.Flights;
import com.airportSystem.airport_system.ServiceProvider.FlightService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
public class FlightController {

    @Autowired
    FlightService flightService;

    @GetMapping("/flight/{id}")
    public Flights getflightById(@PathVariable int id) {
       return flightService.getFlightById(id).orElse(null);
    }

    @GetMapping("/allFlights")
    public Page<Flights> getAllFlights(Pageable pageable) {
        return flightService.getAllFlights(pageable);
    }

    @PostMapping("/addFlight")
    public String addFlight(@RequestBody Flights flight) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int[][] bseats = new int[6][4];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                bseats[i][j] = 0;
            }
        }
        int[][] eseats = new int[15][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                eseats[i][j] = 0;
            }
        }
        flight.setBussinessClass(mapper.writeValueAsString(bseats));
        flight.setEconomicClass(mapper.writeValueAsString(eseats));
        return flightService.addFlight(flight);
    }

    @PutMapping("/updateFlight")
    public String updateFlight(@RequestBody Flights flight) {
        return flightService.updateFlight(flight);
    }

    @DeleteMapping("/deleteFlight/{id}")
    public String deleteFlight(@PathVariable int id) {
        return flightService.deleteFlight(id);
    }

}
