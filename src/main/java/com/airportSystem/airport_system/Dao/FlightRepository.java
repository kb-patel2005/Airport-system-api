package com.airportSystem.airport_system.Dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flights, Integer>{

    // @Query("SELECT f FROM Flights f WHERE f.origin = ?1 AND f.destination = ?2")
    // List<Flights> findByOriginAndDestination(String origin, String destination);
}
