package com.airportSystem.airport_system.Entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendSeat {

    private List<String> seats;
    private boolean isbooked;
    
}
