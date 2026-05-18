package com.airportSystem.airport_system.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airportSystem.airport_system.Entities.FlightDto;
import com.airportSystem.airport_system.Entities.Flights;

@Repository
public interface FlightRepository extends JpaRepository<Flights, Long>{

    @Query("SELECT new com.airportSystem.airport_system.Entities.FlightDto(f.id, f.airline,f.origincountry, f.originstate, f.origincity, f.destinationcountry, f.destinationstate, f.destinationcity, f.price) FROM Flights f")
    Page<FlightDto> findAllFlightsSummary(Pageable pageable);

}
