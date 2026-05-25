package com.airportSystem.airport_system.ServiceProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.BookingRepository;
import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Dao.PassengerRepository;
import com.airportSystem.airport_system.Dao.SeatRepository;
import com.airportSystem.airport_system.Entities.Booking;
import com.airportSystem.airport_system.Entities.DisplaySeats;
import com.airportSystem.airport_system.Entities.FlightAssign;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Entities.Passenger;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Entities.SeatDto;
import com.airportSystem.airport_system.Entities.SeatKey;
import com.airportSystem.airport_system.Entities.SendSeat;
import com.airportSystem.airport_system.Service.AirportService;

import jakarta.transaction.Transactional;

@Service
public class AirportServiceImplementation implements AirportService {

    @Autowired
    private PassengerRepository repository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public Passenger getPassengerData(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public String addPassenger(Passenger passenger) {
        try {
            repository.save(passenger);
            return "Add Passenger Data";
        } catch (Exception e) {
            return "Error adding passenger: " + e.getMessage();
        }

    }

    @Override
    public void updatePassenger(Passenger passenger) {
        Passenger existingPassenger = repository.findById(passenger.getId()).orElse(null);
        if (existingPassenger != null) {
            // passenger.setSeats(existingPassenger.getSeats());
            repository.save(passenger);
        } else {
            System.out.println("Passenger not found with ID: " + passenger.getId());
        }
    }

    @Override
    public String deletePassenger(long id) {
        repository.deleteById(id);
        return "Delete Passenger Data";
    }

    @Override
    public Passenger findByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

    @Override
    @Transactional
    public void addFlightToPassenger(FlightAssign flightAssign) {
        Passenger passenger = repository.findById(flightAssign.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        Flights flight = flightRepository.findById(flightAssign.getFlight().getId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        List<String> seats = new ArrayList<>();

        for (SeatDto seatDto : flightAssign.getSeats()) {
            SeatKey key = new SeatKey(flight.getId(), seatDto.getSeatNumber());
            seats.add(seatDto.getSeatNumber());

            Seat seat = seatRepository.findById(key).orElse(null);

            if (seat != null) {
                if (seat.isBooked()) {
                    throw new RuntimeException("Seat " + seatDto.getSeatNumber() + " is already booked");
                }
            } else {
                throw new RuntimeException("Seat " + seatDto.getSeatNumber() + " not found");
            }

            seat.setBooked(true);
            seatRepository.save(seat);
            Booking booking = new Booking();
            booking.setPassenger(passenger);
            booking.setFlightId(flight.getId());
            booking.setSeatNumber(seatDto.getSeatNumber());
            booking.setStatus("BOOKED");
            booking.setBookingDate(LocalDateTime.now());
            booking.setPrice(getSeatPrice(seatDto.getSeatNumber(), flight.getPrice()));

            bookingRepository.save(booking);
        }

        SendSeat update = new SendSeat(seats,true);

        messagingTemplate.convertAndSend("/topic/messages/" + flightAssign.getFlight().getId(), update);

        repository.save(passenger); 
    }

    @Override
    public List<Booking> getAllSeatsOfPassenger(String id) {
        Long pId = Long.parseLong(id);
        Optional<Passenger> passenger = repository.findById(pId);
        List<Booking> bookings = new ArrayList<>();
        if(passenger.isPresent()){
            return passenger.get().getBookings();
        }
        return bookings;
    }

    @Override
    public void cancelFlightBooking(List<Booking> seats) {
        seats.forEach(booking -> {
            if (booking.getStatus().equals("BOOKED")) {
                SeatKey seatKey = new SeatKey(booking.getFlightId(), booking.getSeatNumber());
                Seat seatEntity = seatRepository.findById(seatKey).orElse(null);
                if (seatEntity != null) {
                    seatEntity.setBooked(false);
                    seatRepository.save(seatEntity);
                }
                booking.setStatus("CANCELLED");
                bookingRepository.save(booking);
            }
        });

    }

    //helper method to calculate seat price based on seat number and base price of flight
    public double getSeatPrice(String seatNumber, int basePrice) {
        
        String rowPart = seatNumber.replaceAll("[^0-9]", "");
        int row = Integer.parseInt(rowPart);

        if (row <= 6) {
            System.out.println("Seat " + seatNumber + " is Business Class");
            return basePrice * 1.5; 
        } else {
            System.out.println("Seat " + seatNumber + " is Economy Class");
            return basePrice; 
        }
    }

}
