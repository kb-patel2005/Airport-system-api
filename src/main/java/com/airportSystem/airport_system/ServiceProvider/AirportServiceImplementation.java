package com.airportSystem.airport_system.ServiceProvider;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.BusinessSeatRepository;
import com.airportSystem.airport_system.Dao.EconomicSeatRepository;
import com.airportSystem.airport_system.Dao.Repository;
import com.airportSystem.airport_system.Entities.BusinessSeats;
import com.airportSystem.airport_system.Entities.EconomicSeats;
import com.airportSystem.airport_system.Entities.FlightAssign;
import com.airportSystem.airport_system.Entities.Passenger;
import com.airportSystem.airport_system.Service.AirportService;

@Service
public class AirportServiceImplementation implements AirportService {

    @Autowired
    private Repository repository;

    @Autowired
    private EconomicSeatRepository economicSeatRepository;

    @Autowired
    private BusinessSeatRepository businessSeatRepository;

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
            passenger.setEconomicseats(existingPassenger.getEconomicseats());
            passenger.setBusinessseats(existingPassenger.getBusinessseats());
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
    public void addFlightId(FlightAssign flightAssign) {
        Optional<Passenger> passenger = repository.findById(flightAssign.getPassengerId());
        if (passenger.isPresent()) {
            Passenger existingPassenger = passenger.get();
            if (flightAssign.getEconomicSeats() != null) {
                List<EconomicSeats> economicSeats = new java.util.ArrayList<>();
                for (EconomicSeats seat : flightAssign.getEconomicSeats()) {
                    EconomicSeats newSeat = economicSeatRepository.findByFlightIdAndRowNumberAndColNumber(flightAssign.getFlight().getId(), seat.getRowNumber(), seat.getColNumber());
                    newSeat.setFlight(seat.getFlight());
                    newSeat.setPassenger(existingPassenger);
                    newSeat.setBooked(true);
                    economicSeatRepository.save(newSeat);
                    economicSeats.add(newSeat);
                }
                existingPassenger.getEconomicseats().addAll(economicSeats);
            }
            if (flightAssign.getBusinessSeats() != null) {
                List<BusinessSeats> businessSeats = new java.util.ArrayList<>();
                for (BusinessSeats seat : flightAssign.getBusinessSeats()) {
                    BusinessSeats newSeat = businessSeatRepository.findByFlightIdAndRowNumberAndColNumber(flightAssign.getFlight().getId(), seat.getRowNumber(), seat.getColNumber());
                    newSeat.setFlight(seat.getFlight());
                    newSeat.setPassenger(existingPassenger);
                    newSeat.setBooked(true);
                    businessSeatRepository.save(newSeat);
                    businessSeats.add(newSeat);
                }
                existingPassenger.getBusinessseats().addAll(businessSeats);
            }
            repository.save(existingPassenger);
        } else {
            System.out.println("Passenger not found with ID: " + flightAssign.getPassengerId());
        }
    }


}
