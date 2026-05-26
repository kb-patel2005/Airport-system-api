package com.airportSystem.airport_system.ServiceProvider;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.BookingRepository;
import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Dao.SeatRepository;
import com.airportSystem.airport_system.Entities.Booking;
import com.airportSystem.airport_system.Entities.FlightDto;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Entities.SeatKey;
import com.airportSystem.airport_system.Service.FlightService;

@Service
public class FlightSerImp implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

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

    @Override
    public void cancelFlightBooking(List<Booking> seats) {
        seats.forEach(booking -> {
            if (booking.getStatus().equals("BOOKED")) {
                booking.setStatus("CANCELLED");
                booking.getBookedSeats().forEach(bookedSeat -> {
                    SeatKey seatKey = new SeatKey(booking.getFlight().getId(), bookedSeat.getSeatNumber());
                    Seat seatEntity = seatRepository.findById(seatKey).orElse(null);
                    if (seatEntity != null) {
                        seatEntity.setBooked(false);
                        seatRepository.save(seatEntity);
                    }
                });
                bookingRepository.save(booking);
            }
        });
    }

    @Override
    public List<FlightDto> FlightByOriginAndDestination(String oCountry, String oState, String ocity, String dCountry,
            String dState, String dCity) {
        List<FlightDto> flightDto = flightRepository
                .findByOrigincountryAndOriginstateAndOrigincityAndDestinationcountryAndDestinationstateAndDestinationcity(
                        oCountry, oState, ocity, dCountry, dState, dCity);
        if (flightDto.size() > 0) {
            return flightDto;
        }
        return null;
    }
}