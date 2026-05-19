package com.airportSystem.airport_system.Entities;

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

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonBackReference("SeatsRef")
    private Flights flight;

    private String seatNumber;
    
}

