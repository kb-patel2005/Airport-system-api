package com.airportSystem.airport_system.ServiceProvider;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.BusinessSeatRepository;
import com.airportSystem.airport_system.Dao.EconomicSeatRepository;
import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Dao.SeatRepository;
import com.airportSystem.airport_system.Entities.BusinessSeats;
import com.airportSystem.airport_system.Entities.EconomicSeats;
import com.airportSystem.airport_system.Entities.FlightDto;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Service.FlightService;

@Service
public class FlightSerImp implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    // @Autowired
    // private EconomicSeatRepository economicSeatRepository;

    // @Autowired
    // private BusinessSeatRepository businessSeatRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public Page<FlightDto> getAllFlights(Pageable pageable) {
        return flightRepository.findAllFlightsSummary(pageable);
    }

    @Override
    public Optional<Flights> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    public String addFlight(Flights flight) {
        flightRepository.save(flight);
        return "Flight added successfully";
    }

    @Override
    public Flights saveFlight(Flights flight) {
        return flightRepository.save(flight);
    }

    @Override
    public String updateFlight(Flights flight) {
        flightRepository.save(flight);
        return "Flight updated successfully";
    }

    @Override
    public String deleteFlight(Long id) {
        flightRepository.deleteById(id);
        return "Flight cancelled successfully";
    }

    @Override
    public List<List<Boolean>> getSeatsByClass(Long id) {
        Optional<Flights> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()) {
            Flights flight = optionalFlight.get();

            char[] columns = { 'A', 'B', 'C', 'D', 'E', 'F' };

            List<List<Boolean>> seatsByClass = new ArrayList<>();

            int totalRows = 20;

            for (int row = 1; row <= totalRows; row++) {
                List<Boolean> rowSeats = new ArrayList<>();
                for (char col : columns) {
                    String seatNumber = col + String.valueOf(row);
                    Seat seat = flight.getSeats()
                            .stream()
                            .filter(s -> s.getId().getSeatNumber().equals(seatNumber))
                            .findFirst()
                            .orElse(null);

                    rowSeats.add(seat != null && seat.isBooked());
                }
                seatsByClass.add(rowSeats);
            }

            return seatsByClass;
        } else {
            throw new IllegalArgumentException("Flight not found");
        }
    }

    // @Override
    // public String cancelEconomicFlight(EconomicSeats seat) {
    //     EconomicSeats existingSeat = economicSeatRepository.findById(seat.getId()).orElse(null);
    //     if (existingSeat != null) {
    //         existingSeat.setBooked(false);
    //         existingSeat.setPassenger(null);
    //         economicSeatRepository.save(existingSeat);
    //         return "Economic seat cancelled successfully";
    //     } else {
    //         return "Economic seat not found";
    //     }
    // }

    // @Override
    // public String cancelBusinessFlight(BusinessSeats seat) {
    //     BusinessSeats existingSeat = businessSeatRepository.findById(seat.getId()).orElse(null);
    //     if (existingSeat != null) {
    //         existingSeat.setBooked(false);
    //         existingSeat.setPassenger(null);
    //         businessSeatRepository.save(existingSeat);
    //         return "Business seat cancelled successfully";
    //     } else {
    //         return "Business seat not found";
    //     }
    // }

    @Override
    public void cancelFlightBooking(List<Seat> seats) {
        seats.forEach(seat -> {
            if (seat.isBooked()) {
                seat.setBooked(false);
                seat.setPassenger(null);
                seatRepository.save(seat);
            }
        });
        
    }
}