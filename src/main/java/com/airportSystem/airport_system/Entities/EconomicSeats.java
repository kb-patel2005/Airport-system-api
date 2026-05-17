package com.airportSystem.airport_system.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class EconomicSeats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seat_row")
    private String rowNumber;

    @Column(name = "seat_col")
    private String colNumber;
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonBackReference
    private Flights flight;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    @JsonBackReference
    private Passenger passenger;

    public EconomicSeats(String rowNumber, String colNumber, boolean isBooked) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.isBooked = isBooked;
    }
}
