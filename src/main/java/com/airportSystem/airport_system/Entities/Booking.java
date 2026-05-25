package com.airportSystem.airport_system.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "bookings",
    indexes = {
        @Index(name = "idx_passenger_id", columnList = "passenger_id")
    }
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long flightId;

    private String seatNumber;

    private Double price;

    private LocalDateTime bookingDate;

    private String status;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    @JsonBackReference("PassengerBookingRef")
    private Passenger passenger;
}
