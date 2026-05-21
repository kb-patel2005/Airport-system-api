package com.airportSystem.airport_system.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Embeddable
@EqualsAndHashCode
public class SeatKey implements Serializable {

    @Column(name = "flight_id")
    private Long flightId;   // just the ID, not the entity

    @Column(name = "seat_number")
    private String seatNumber;
}

