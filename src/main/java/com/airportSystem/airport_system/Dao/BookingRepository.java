package com.airportSystem.airport_system.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airportSystem.airport_system.Entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long>{
    
}
