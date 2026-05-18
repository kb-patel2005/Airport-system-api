package com.airportSystem.airport_system.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airportSystem.airport_system.Entities.BusinessSeats;
import com.airportSystem.airport_system.Entities.Flights;

@Repository
public interface BusinessSeatRepository extends JpaRepository<BusinessSeats, Long> {

    public BusinessSeats findByFlightAndRowNumberAndColNumber(Flights flight, String rowNumber, String colNumber);

    BusinessSeats findByFlight_IdAndRowNumberAndColNumber(Long id, String rowNumber, String colNumber);
    
}
