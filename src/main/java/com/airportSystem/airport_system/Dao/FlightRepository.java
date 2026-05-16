package com.airportSystem.airport_system.Dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airportSystem.airport_system.Entities.Flights;

@Repository
public interface FlightRepository extends JpaRepository<Flights, Integer>{

}
