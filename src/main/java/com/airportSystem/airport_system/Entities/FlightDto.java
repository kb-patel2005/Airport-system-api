package com.airportSystem.airport_system.Entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlightDto {
    private Long id;
    private String airline;
    private String origincountry;
    private String originstate;
    private String origincity;
    private String destinationcountry;
    private String destinationstate;
    private String destinationcity;
    private int price;
    
    public FlightDto(Long id, String airline, String origincountry, String originstate, String origincity,
            String destinationcountry, String destinationstate, String destinationcity, int price) {
        this.id = id;
        this.airline = airline;
        this.origincountry = origincountry;
        this.originstate = originstate;
        this.origincity = origincity;
        this.destinationcountry = destinationcountry;
        this.destinationstate = destinationstate;
        this.destinationcity = destinationcity;
        this.price = price;
    }
}
