package com.airportSystem.airport_system.ServiceProvider;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.airportSystem.airport_system.Dao.Flights;
import com.airportSystem.airport_system.Dao.Passenger;

public interface Repository extends JpaRepository<Passenger, Integer> {

    @Query("SELECT p FROM Passenger p WHERE username = ?1 AND password = ?2")
    Passenger findByUsernameAndPassword(String username, String password);

    @Modifying
    @Transactional
    @Query("UPDATE Passenger p SET p.flight = :flight, p.seatno = :seatNo WHERE p.passportNumber = :passportNumber")
    void addFlightId(@Param("passportNumber") int passportNumber,
            @Param("flight") Flights flight,
            @Param("seatNo") String seatNo);

    @Modifying
    @Transactional
    @Query("UPDATE Passenger p SET p.flight = null, p.seatno = null WHERE p.passportNumber = :passportNumber")
    void cancelPassengerBooking(@Param("passportNumber") int passportNumber);

    @Query("SELECT p FROM Passenger p WHERE p.passportNumber = ?1")
    Passenger getPassengerData(int id);

    Optional<Passenger> findByUsername(String username);

    Passenger findByPassportNumber(int passengerId);

}
