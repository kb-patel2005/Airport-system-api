package com.airportSystem.airport_system.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
   
    private Long bookingId;

    private FlightDto flight;

    private LocalDateTime bookingDate;

    private String status;

    private List<BookedSeatDto> bookedSeats = new ArrayList<>();

}
