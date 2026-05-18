package com.airportSystem.airport_system.ServiceProvider;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.BusinessSeatRepository;
import com.airportSystem.airport_system.Dao.EconomicSeatRepository;
import com.airportSystem.airport_system.Dao.FlightRepository;
import com.airportSystem.airport_system.Entities.BusinessSeats;
import com.airportSystem.airport_system.Entities.EconomicSeats;
import com.airportSystem.airport_system.Entities.FlightDto;
import com.airportSystem.airport_system.Entities.Flights;
import com.airportSystem.airport_system.Service.FlightService;

@Service
public class FlightSerImp implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private EconomicSeatRepository economicSeatRepository;

    @Autowired
    private BusinessSeatRepository businessSeatRepository;

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
    public List<List<Boolean>> getSeatsByClass(Long id, String className) {
        Optional<Flights> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()) {
            Flights flight = optionalFlight.get();
            List<List<Boolean>> seatsByClass = new ArrayList<>();
            if (className.equals("Bussiness")) {
                for (BusinessSeats seat : flight.getBusinessseats()) {
                    if (seatsByClass.size() <= Integer.parseInt(seat.getRowNumber())) {
                        seatsByClass.add(new ArrayList<>());
                    }
                    seatsByClass.get(Integer.parseInt(seat.getRowNumber()) - 1).add(seat.isBooked());
                }
            } else {
                for (EconomicSeats seat : flight.getEconomicseats()) {
                    if (seatsByClass.size() <= Integer.parseInt(seat.getRowNumber())) {
                        seatsByClass.add(new ArrayList<>());
                    }
                    seatsByClass.get(Integer.parseInt(seat.getRowNumber()) - 1).add(seat.isBooked());
                }
            }
            return seatsByClass;
        } else {
            throw new IllegalArgumentException("Flight not found");
        }

    }

    @Override
    public String cancelEconomicFlight(EconomicSeats seat) {
        EconomicSeats existingSeat = economicSeatRepository.findById(seat.getId()).orElse(null);
        if (existingSeat != null) {
            existingSeat.setBooked(false);
            existingSeat.setPassenger(null);
            economicSeatRepository.save(existingSeat);
            return "Economic seat cancelled successfully";
        } else {
            return "Economic seat not found";
        }
    }

    @Override
    public String cancelBusinessFlight(BusinessSeats seat) {
        BusinessSeats existingSeat = businessSeatRepository.findById(seat.getId()).orElse(null);
        if (existingSeat != null) {
            existingSeat.setBooked(false);
            existingSeat.setPassenger(null);
            businessSeatRepository.save(existingSeat);
            return "Business seat cancelled successfully";
        } else {
            return "Business seat not found";
        }
    }

}