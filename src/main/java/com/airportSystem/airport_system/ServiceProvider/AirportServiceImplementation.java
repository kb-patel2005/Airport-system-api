package com.airportSystem.airport_system.ServiceProvider;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.Repository;
import com.airportSystem.airport_system.Entities.Passenger;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Service.AirportService;

@Service
public class AirportServiceImplementation implements AirportService {

    @Autowired
    private Repository repository;

    @Override
    public Passenger getPassengerData(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public String addPassenger(Passenger passenger) {
        try {
            repository.save(passenger);
            return "Add Passenger Data";
        } catch (Exception e) {
            return "Error adding passenger: " + e.getMessage();
        }

    }

    @Override
    public void updatePassenger(Passenger passenger) {
        Passenger existingPassenger = repository.findById(passenger.getId()).orElse(null);
        if (existingPassenger != null) {
            passenger.setSeats(passenger.getSeats());
            repository.save(passenger);
        } else {
            System.out.println("Passenger not found with ID: " + passenger.getId());
        }
    }

    @Override
    public String deletePassenger(long id) {
        repository.deleteById(id);
        return "Delete Passenger Data";
    }

    @Override
    public Passenger findByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

    @Override
    public void addFlightId(String passengerId, String flightId, List<Seat> sendedseat) {
        Optional<Passenger> passenger = repository.findById(Long.parseLong(passengerId));
        if (passenger.isPresent()) {
            Passenger existingPassenger = passenger.get();
            List<Seat> existingSeats = existingPassenger.getSeats();
            existingSeats.addAll(sendedseat);
            existingPassenger.setSeats(existingSeats);
            repository.save(existingPassenger);
        } else {
            System.out.println("Passenger not found with ID: " + passengerId);
        }
    }

}
