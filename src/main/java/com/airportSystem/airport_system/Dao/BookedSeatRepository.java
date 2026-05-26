package com.airportSystem.airport_system.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airportSystem.airport_system.Entities.BookedSeat;

public interface BookedSeatRepository extends JpaRepository<BookedSeat, Long> {
    
}
