package com.airportSystem.airport_system.Entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String gender;
    private int age;
    private long phone;
    private String email;
    private String imgname;
    private String imgcontenttype;
    @Lob
    private byte[] image;
    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("PassengerSeatsRef")
    private List<Seat> seats = new java.util.ArrayList<>();
    // @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonManagedReference("businessSeatsPassengerRef")
    // private List<BusinessSeats> businessseats = new java.util.ArrayList<>();

}
