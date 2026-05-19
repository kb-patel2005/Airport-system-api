package com.airportSystem.airport_system.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "seats",
    indexes = {
        @Index(name = "idx_flight_id", columnList = "flight_id"),
        @Index(name = "idx_passenger_id", columnList = "passenger_id")
    }
)
public class Seat {

    @EmbeddedId
    private SeatKey id;  // PRIMARY KEY = flight + seatNumber

    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    @JsonBackReference("PassengerSeatsRef")
    private Passenger passenger;
}
