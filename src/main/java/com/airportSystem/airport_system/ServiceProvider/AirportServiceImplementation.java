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
    public Passenger getPassengerData(int id) {
        return repository.getPassengerData(id);
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
        Passenger existingPassenger = repository.findByPassportNumber(passenger.getPassportNumber());
        if (existingPassenger != null) {
            passenger.setSeats(passenger.getSeats());
            repository.save(passenger);
        } else {
            System.out.println("Passenger not found with passport number: " + passenger.getPassportNumber());
        }
    }

    @Override
    public String deletePassenger(int id) {
        repository.deleteById(id);
        return "Delete Passenger Data";
    }

    @Override
    public Passenger findByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

    @Override
    public void addFlightId(String passengerId, String flightId, List<Seat> sendedseat) {
        Optional<Passenger> passenger = repository.findById(Integer.parseInt(passengerId));
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
