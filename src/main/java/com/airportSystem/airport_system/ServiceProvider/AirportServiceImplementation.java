package com.airportSystem.airport_system.ServiceProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.BookedSeatRepository;
import com.airportSystem.airport_system.Dao.BookingRepository;
import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Dao.PassengerRepository;
import com.airportSystem.airport_system.Dao.SeatRepository;
import com.airportSystem.airport_system.Entities.BookedSeat;
import com.airportSystem.airport_system.Entities.BookedSeatDto;
import com.airportSystem.airport_system.Entities.Booking;
import com.airportSystem.airport_system.Entities.BookingDto;
import com.airportSystem.airport_system.Entities.DisplaySeats;
import com.airportSystem.airport_system.Entities.FlightAssign;
import com.airportSystem.airport_system.Entities.FlightDto;
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
    private BookedSeatRepository bookedSeatRepository;

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

        Booking booking = new Booking();
        booking.setPassenger(passenger);
        booking.setFlight(flight);
        booking.setStatus("BOOKED");
        booking.setBookingDate(LocalDateTime.now());

        List<BookedSeat> bookedSeats = new ArrayList<>();
        List<String> selectedSeats = new ArrayList<>();

        for (SeatDto seatDto : flightAssign.getSeats()) {

            String seatNumber = seatDto.getSeatNumber();

            SeatKey seatKey = new SeatKey(flight.getId(), seatNumber);

            Seat seat = seatRepository.findById(seatKey)
                    .orElseThrow(() -> new RuntimeException("Seat " + seatNumber + " not found"));

            if (seat.isBooked()) {
                throw new RuntimeException("Seat " + seatNumber + " already booked");
            }

            seat.setBooked(true);
            seatRepository.save(seat);

            BookedSeat bookedSeat = new BookedSeat();
            bookedSeat.setSeatNumber(seatNumber);
            bookedSeat.setStatus("BOOKED");
            bookedSeat.setBooking(booking);
            bookedSeat.setSeatPrice(getSeatPrice(List.of(seatNumber), flight.getPrice()));

            bookedSeats.add(bookedSeat);
            selectedSeats.add(seatNumber);
        }

        booking.setBookedSeats(bookedSeats);

        bookingRepository.save(booking);

        SendSeat update = new SendSeat(selectedSeats, true);

        messagingTemplate.convertAndSend(
                "/topic/messages/" + flight.getId(),
                update);
    }

    @Override
    public List<BookingDto> getAllSeatsOfPassenger(String id) {
        Long pId = Long.parseLong(id);
        Optional<Passenger> passenger = repository.findById(pId);
        List<BookingDto> bookingDtos = new ArrayList<>();
        if (passenger.isPresent()) {
            passenger.get().getBookings().forEach(booking -> {
                FlightDto flightDto = new FlightDto();
                Flights flight = booking.getFlight();
                flightDto.setId(flight.getId());
                flightDto.setOrigincountry(flight.getOrigincountry());
                flightDto.setOriginstate(flight.getOriginstate());
                flightDto.setOrigincity(flight.getOrigincity());
                flightDto.setDestinationcountry(flight.getDestinationcountry());
                flightDto.setDestinationstate(flight.getDestinationstate());
                flightDto.setDestinationcity(flight.getDestinationcity());
                flightDto.setAirline(flight.getAirline());
                flightDto.setPrice(flight.getPrice());
                booking.getBookedSeats().forEach(bookedSeat -> {
                    BookedSeatDto bookedSeatDto = new BookedSeatDto();
                    bookedSeatDto.setId(bookedSeat.getId());
                    bookedSeatDto.setSeatNumber(bookedSeat.getSeatNumber());
                    bookedSeatDto.setSeatPrice(bookedSeat.getSeatPrice());
                    bookedSeatDto.setStatus(bookedSeat.getStatus());
                });
                BookingDto bookingDto = new BookingDto();
                bookingDto.setBookingId(booking.getBookingId());
                bookingDto.setFlight(flightDto);
                bookingDto.setBookingDate(booking.getBookingDate());
                bookingDto.setStatus(booking.getStatus());
                bookingDtos.add(bookingDto);
            });
        }
        return bookingDtos;
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
                    bookedSeat.setStatus("CANCELLED");
                    bookedSeatRepository.save(bookedSeat);
                });
                bookingRepository.save(booking);
            }
        });
    }

    // price calculate karava mate function
    public double getSeatPrice(List<String> seatNumbers, int basePrice) {
        double totalPrice = 0.0;
        for (String seat : seatNumbers) {
            String rowPart = seat.replaceAll("[^0-9]", "");
            int row = Integer.parseInt(rowPart);
            if (row <= 6) {
                totalPrice += basePrice * 1.5;

            } else {
                totalPrice += basePrice;
            }
        }
        return totalPrice;
    }

    @Override
    public BookedSeat cancelBookedSeatById(BookedSeat bookedSeat) {
        if (bookedSeat.getStatus().equals("BOOKED")) {
            bookedSeat.setStatus("CANCELLED");
            SeatKey seatKey = new SeatKey(bookedSeat.getBooking().getFlight().getId(), bookedSeat.getSeatNumber());
            Seat seatEntity = seatRepository.findById(seatKey).orElse(null);
            if (seatEntity != null) {
                seatEntity.setBooked(false);
                seatRepository.save(seatEntity);
            }
            return bookedSeatRepository.save(bookedSeat);
        } else {
            throw new RuntimeException("Booked seat is not in BOOKED status");
        }
    }

}
