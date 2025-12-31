package com.airportSystem.airport_system.Dao;

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
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "flights_gen")
    @TableGenerator(name = "flights_gen", table = "flights_seq", pkColumnName = "seq_name", valueColumnName = "seq_count", pkColumnValue = "flights", allocationSize = 1)
    private int flightId;

    @Column(columnDefinition = "json")
    private String bussinessClass;
    @Column(columnDefinition = "json")
    private String economicClass;

    private String airline;
    private String origincountry;
    private String originstate;
    private String origincity;
    private String destinationcountry;
    private String destinationstate;
    private String destinationcity;
    private int price;

}
