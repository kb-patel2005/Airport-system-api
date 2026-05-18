package com.airportSystem.airport_system.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airportSystem.airport_system.Entities.EconomicSeats;

public interface EconomicSeatRepository extends JpaRepository<EconomicSeats, Long> {
    
    public EconomicSeats findByFlightIdAndRowNumberAndColNumber(Long flightId, String rowNumber, String colNumber);

}
