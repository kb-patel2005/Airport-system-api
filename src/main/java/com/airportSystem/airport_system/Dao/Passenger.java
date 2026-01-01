package com.airportSystem.airport_system.Dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.TableGenerator;
// import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int passportNumber;
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
    private String seatno;
    @ManyToOne
    private Flights flight;

}
