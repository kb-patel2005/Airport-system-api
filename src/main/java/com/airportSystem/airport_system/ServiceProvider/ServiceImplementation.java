package com.airportSystem.airport_system.ServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.Flights;
import com.airportSystem.airport_system.Dao.Passenger;

@Service
public class ServiceImplementation implements AirportService{

    @Autowired
    Repository repository;

    @Override
    public Passenger getPassengerData(int id) {
        return repository.getPassengerData(id);
    }

    @Override
    public String addPassenger(Passenger passenger) {
        try {
            repository.save(passenger);
            return "Add Passenger Data";
        } catch (Exception e) {
            // Handle exception if needed
            return "Error adding passenger: " + e.getMessage();
        }
        
    }

    @Override
    public void updatePassenger(Passenger passenger) {
        repository.save(passenger);
    }

    @Override
    public String deletePassenger(int id) {
        repository.deleteById(id);
        return "Delete Passenger Data";
    }

    @Override
    public Passenger findByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }

    @Override
    public void addFlightId(int passengerId, Flights flightId, String seatNo) {
        repository.addFlightId(passengerId, flightId, seatNo);
    }

    @Override
    public String cancelFlight(int id) {
        repository.cancelPassengerBooking(id);
        return "Flight cancelled successfully";
    }
    
}
