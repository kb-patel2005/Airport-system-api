package com.airportSystem.airport_system.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airportSystem.airport_system.Entities.EconomicSeats;
import com.airportSystem.airport_system.Entities.Flights;

public interface EconomicSeatRepository extends JpaRepository<EconomicSeats, Long> {
    
    public EconomicSeats findByFlightAndRowNumberAndColNumber(Flights flight, String rowNumber, String colNumber);

    public EconomicSeats findByFlight_IdAndRowNumberAndColNumber(Long id, String rowNumber, String colNumber);

}
