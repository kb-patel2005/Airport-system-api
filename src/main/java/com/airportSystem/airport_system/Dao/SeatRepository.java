package com.airportSystem.airport_system.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airportSystem.airport_system.Entities.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    
}
