package com.airportSystem.airport_system.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airportSystem.airport_system.Entities.EconomicSeats;
import com.airportSystem.airport_system.Entities.Flights;

@Repository
public interface EconomicSeatRepository extends JpaRepository<EconomicSeats, Long> {
    
    public EconomicSeats findByFlightAndRowNumberAndColNumber(Flights flight, String rowNumber, String colNumber);

    public EconomicSeats findByFlight_IdAndRowNumberAndColNumber(Long id, String rowNumber, String colNumber);

}
