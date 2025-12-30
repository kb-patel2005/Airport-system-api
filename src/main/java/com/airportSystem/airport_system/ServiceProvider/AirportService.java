package com.airportSystem.airport_system.ServiceProvider;

import com.airportSystem.airport_system.Dao.Flights;
import com.airportSystem.airport_system.Dao.Passenger;

public interface AirportService {
    
    public Passenger getPassengerData(int id);

    public String addPassenger(Passenger passenger);

    public void updatePassenger(Passenger passenger);

    public String deletePassenger(int id);

    public Passenger findByUsernameAndPassword(String username, String password);

    public void addFlightId(int passengerId,Flights flightId, String seatNo);

    public String cancelFlight(int id);

}
