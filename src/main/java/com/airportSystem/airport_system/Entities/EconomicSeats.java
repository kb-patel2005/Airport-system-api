package com.airportSystem.airport_system.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
// @Entity
// @Table(
//     name = "economic_seats",
//     indexes = {
//         @jakarta.persistence.Index(name = "idx_flight_id", columnList = "flight_id"),
//         @jakarta.persistence.Index(name = "idx_passenger_id", columnList = "passenger_id")
//     }
// )
public class EconomicSeats {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seat_row")
    private String rowNumber;

    @Column(name = "seat_col")
    private String colNumber;
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonBackReference("economicSeatsRef")
    private Flights flight;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    @JsonBackReference("economicSeatsPassengerRef")
    private Passenger passenger;

    public EconomicSeats(String rowNumber, String colNumber, boolean isBooked) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.isBooked = isBooked;
    }
}
