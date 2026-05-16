package com.airportSystem.airport_system.Service;

import java.util.List;

import com.airportSystem.airport_system.Entities.Passenger;
import com.airportSystem.airport_system.Entities.Seat;

public interface AirportService {
    
    public Passenger getPassengerData(int id);

    public String addPassenger(Passenger passenger);

    public void updatePassenger(Passenger passenger);

    public String deletePassenger(int id);

    public Passenger findByEmailAndPassword(String email, String password);

    public void addFlightId(String passengerId, String flightId, List<Seat> sendedseat);

}
