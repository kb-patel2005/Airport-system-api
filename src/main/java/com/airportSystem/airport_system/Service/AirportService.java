package com.airportSystem.airport_system.Service;

import java.util.List;

import com.airportSystem.airport_system.Entities.BookedSeat;
import com.airportSystem.airport_system.Entities.Booking;
import com.airportSystem.airport_system.Entities.FlightAssign;
import com.airportSystem.airport_system.Entities.Passenger;

public interface AirportService {
    
    public Passenger getPassengerData(long id);

    public String addPassenger(Passenger passenger);

    public void updatePassenger(Passenger passenger);

    public String deletePassenger(long id);

    public Passenger findByEmailAndPassword(String email, String password);

    public void addFlightToPassenger(FlightAssign flightAssign);

    public List<Booking> getAllSeatsOfPassenger(String id);

    public void cancelFlightBooking(List<Booking> seats);

    public BookedSeat cancelBookedSeatById(BookedSeat bookedSeat);

}
