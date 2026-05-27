package com.airportSystem.airport_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airportSystem.airport_system.Dao.BookedSeatRepository;
import com.airportSystem.airport_system.Entities.BookedSeat;
import com.airportSystem.airport_system.Entities.BookingDto;
import com.airportSystem.airport_system.Entities.FlightAssign;
import com.airportSystem.airport_system.Entities.Passenger;
import com.airportSystem.airport_system.Service.AirportService;

@RestController
@RequestMapping("api")
public class controller {

    @Autowired
    private AirportService service;

    @Autowired
    private BookedSeatRepository bookedSeatRepository;

    @GetMapping("/welcome/passenger")
    public Passenger welcome() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.getPassengerData(email);
    }

    @PutMapping("/bookFlight")
    public void updatePassenger(@RequestBody FlightAssign request) {
        try {
            service.addFlightToPassenger(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/deletePassenger")
    public String deletePassenger() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Passenger passenger = service.getPassengerData(email);
        if (passenger == null) {
            return "Passenger already deleted or not found";
        }
        return service.deletePassenger(email);
    }

    @GetMapping("/passengerSeats")
    public List<BookingDto> getAllPassengerSeats() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Passenger passenger = service.getPassengerData(email);
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger not found with email: " + email);
        }
        return service.getAllSeatsOfPassenger(String.valueOf(passenger.getId()));
    }

    @PutMapping("/cancelBooking")
    public void cancelBooking(@RequestBody List<String> bookingIds) {
        service.cancelFlightBooking(bookingIds);
    }

    @PutMapping("/cancelBookedSeat/{id}")
    public BookedSeat cancelBookedSeat(@PathVariable("id") String bookedSeatId) {
        
        BookedSeat bookedSeat = bookedSeatRepository.findById(Long.parseLong(bookedSeatId)).orElse(null);
        if (bookedSeat == null) {
            throw new IllegalArgumentException("Booked seat not found with ID: " + bookedSeatId);
        }
        return service.cancelBookedSeatById(bookedSeat);
    }

}
