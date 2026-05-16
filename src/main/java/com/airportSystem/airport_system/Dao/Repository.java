package com.airportSystem.airport_system.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.airportSystem.airport_system.Entities.Passenger;

public interface Repository extends JpaRepository<Passenger, Integer> {

    @Query("SELECT p FROM Passenger p WHERE email = ?1 AND password = ?2")
    Passenger findByEmailAndPassword(String email, String password);

    @Query("SELECT p FROM Passenger p WHERE p.passportNumber = ?1")
    Passenger getPassengerData(int id);

    Optional<Passenger> findByEmail(String email);

    Passenger findByPassportNumber(int passengerId);

}
