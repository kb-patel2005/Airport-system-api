package com.airportSystem.airport_system.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightId;
    private Boolean[][] businessClass;
    private Boolean[][] economyClass;
    private String airline;
    private String origincountry;
    private String originstate;
    private String origincity;
    private String destinationcountry;
    private String destinationstate;
    private String destinationcity;
    private int price;

}
