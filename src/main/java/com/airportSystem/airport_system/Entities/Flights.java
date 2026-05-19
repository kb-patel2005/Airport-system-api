package com.airportSystem.airport_system.Entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private Long id;
    private String airline;
    private String origincountry;
    private String originstate;
    private String origincity;
    private String destinationcountry;
    private String destinationstate;
    private String destinationcity;
    private int price;
    @OneToMany(mappedBy = "id.flight", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("SeatsRef")
    private List<Seat> seats = new java.util.ArrayList<>();
    // @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonManagedReference("businessSeatsRef")
    // private List<BusinessSeats> businessseats = new java.util.ArrayList<>();

}
