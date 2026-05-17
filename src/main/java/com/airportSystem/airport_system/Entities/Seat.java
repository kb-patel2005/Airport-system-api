package com.airportSystem.airport_system.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatClass;
    @Column(name = "seat_row")
    private String rowNumber;

    @Column(name = "seat_col")
    private String colNumber;
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flights flight;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    public Seat(String seatClass, String rowNumber, String colNumber, boolean isBooked) {
        this.seatClass = seatClass;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.isBooked = isBooked;
    }
}
