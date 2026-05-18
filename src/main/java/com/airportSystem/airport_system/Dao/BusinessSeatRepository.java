package com.airportSystem.airport_system.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airportSystem.airport_system.Entities.BusinessSeats;

public interface BusinessSeatRepository extends JpaRepository<BusinessSeats, Long> {

    BusinessSeats findByFlight_IdAndRowNumberAndColNumber(Long id, String rowNumber, String colNumber);
    
}
