package com.airportSystem.airport_system.Service;

import com.airportSystem.airport_system.Entities.FlightAssign;
import com.airportSystem.airport_system.Entities.Passenger;

public interface AirportService {
    
    public Passenger getPassengerData(long id);

    public String addPassenger(Passenger passenger);

    public void updatePassenger(Passenger passenger);

    public String deletePassenger(long id);

    public Passenger findByEmailAndPassword(String email, String password);

    public void addFlightId(FlightAssign flightAssign);

}
