package com.airportSystem.airport_system.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flights flight;

    private Double totalPrice;

    private LocalDateTime bookingDate;

    private String status;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    @JsonBackReference("PassengerBookingRef")
    private Passenger passenger;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookedSeat> bookedSeats = new ArrayList<>();
}