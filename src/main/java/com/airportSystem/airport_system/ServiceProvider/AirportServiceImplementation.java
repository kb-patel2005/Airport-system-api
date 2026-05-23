package com.airportSystem.airport_system.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.BusinessSeatRepository;
import com.airportSystem.airport_system.Dao.EconomicSeatRepository;
import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Dao.PassengerRepository;
import com.airportSystem.airport_system.Dao.SeatRepository;
import com.airportSystem.airport_system.Entities.BusinessSeats;
import com.airportSystem.airport_system.Entities.DisplaySeats;
import com.airportSystem.airport_system.Entities.EconomicSeats;
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
            passenger.setSeats(existingPassenger.getSeats());
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
                // create new seat if not exists
                seat = new Seat();
                seat.setId(key);
                seat.setFlight(flight);
            }

            seat.setBooked(true);
            seat.setPassenger(passenger);

            passenger.getSeats().add(seat);
            
        }

        SendSeat update = new SendSeat(seats,true);

        messagingTemplate.convertAndSend("/topic/messages/" + flightAssign.getFlight().getId(), update);

        repository.save(passenger); 
    }

    @Override
    public List<DisplaySeats> getAllSeatsOfPassenger(String id) {
        Long pId = Long.parseLong(id);
        Optional<Passenger> passenger = repository.findById(pId);
        List<DisplaySeats> seats = new ArrayList<>();
        if(passenger.isPresent()){
            for (Seat seat : passenger.get().getSeats()) {
                // Optional<Flights> flight = flightRepository.findById(seat.getId().getFlightId());
                DisplaySeats dSeats = new DisplaySeats();
                dSeats.setFlightId(seat.getId().getFlightId());
                dSeats.setFrom(seat.getFlight().getOrigincountry()+" ,"+seat.getFlight().getOriginstate()+" ,"+seat.getFlight().getOrigincity());
                dSeats.setTo(seat.getFlight().getDestinationcity()+" ,"+seat.getFlight().getDestinationstate()+" ,"+seat.getFlight().getDestinationcountry());
                dSeats.setSeatNumber(seat.getId().getSeatNumber());
                int price = seat.getFlight().getPrice();
                int seatRow = Integer.parseInt(seat.getId().getSeatNumber().substring(1));
                if(seatRow <= 6){
                    dSeats.setPrice(price);
                }else{
                    dSeats.setPrice(Math.ceil(price * 1.5));
                }
                seats.add(dSeats);
            }
            return seats;
        }
        return null;
    }

}
