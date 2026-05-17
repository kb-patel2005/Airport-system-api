package com.airportSystem.airport_system.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.airportSystem.airport_system.Entities.Passenger;

public interface Repository extends JpaRepository<Passenger, Long> {

    @Query("SELECT p FROM Passenger p WHERE email = ?1 AND password = ?2")
    Passenger findByEmailAndPassword(String email, String password);

    Optional<Passenger> findByEmail(String email);

}
