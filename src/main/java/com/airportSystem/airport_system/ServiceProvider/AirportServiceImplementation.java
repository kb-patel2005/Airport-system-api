package com.airportSystem.airport_system.ServiceProvider;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.BusinessSeatRepository;
import com.airportSystem.airport_system.Dao.EconomicSeatRepository;
import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Dao.PassengerRepository;
import com.airportSystem.airport_system.Dao.SeatRepository;
import com.airportSystem.airport_system.Entities.BusinessSeats;
import com.airportSystem.airport_system.Entities.EconomicSeats;
import com.airportSystem.airport_system.Entities.FlightAssign;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Passenger;
import com.airportSystem.airport_system.Service.AirportService;

@Service
public class AirportServiceImplementation implements AirportService {

    @Autowired
    private PassengerRepository repository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

    // @Autowired
    // private EconomicSeatRepository economicSeatRepository;

    // @Autowired
    // private BusinessSeatRepository businessSeatRepository;

    // @Autowired
    // private FlightRepository flightRepository;

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
            passenger.setSeats(existingPassenger.getSeats());
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
    public void addFlightToPassenger(FlightAssign flightAssign) {
        Optional<Passenger> passenger = repository.findById(flightAssign.getPassengerId());
        if (passenger.isPresent()) {
            Passenger existingPassenger = passenger.get();
            existingPassenger.getSeats().addAll(flightAssign.getFlight().getSeats());
            repository.save(existingPassenger);
        } else {
            System.out.println("Passenger not found with ID: " + flightAssign.getPassengerId());
        }
    }


}
