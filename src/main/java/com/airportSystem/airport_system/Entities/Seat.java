package com.airportSystem.airport_system.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatid;

    private String seatClass;

    private String seatNumber;
    
    private String flightId;

    private String origin;

    private String destination;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    
}

