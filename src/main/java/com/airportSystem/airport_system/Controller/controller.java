package com.airportSystem.airport_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.airportSystem.airport_system.Entities.BusinessSeats;
import com.airportSystem.airport_system.Entities.DisplaySeats;
import com.airportSystem.airport_system.Entities.EconomicSeats;
import com.airportSystem.airport_system.Entities.FlightAssign;
import com.airportSystem.airport_system.Entities.LoginStaff;
import com.airportSystem.airport_system.Entities.Passenger;
import com.airportSystem.airport_system.Entities.Seat;
import com.airportSystem.airport_system.Entities.Stafftextdata;
import com.airportSystem.airport_system.Service.AirportService;
import com.airportSystem.airport_system.Service.FlightService;

@RestController
@CrossOrigin("*")
@RequestMapping("api")
public class controller {

    @Autowired
    private AirportService service;

    @Autowired
    private FlightService flightService;

    @GetMapping("/welcome/passenger/{id}")
    public Passenger welcome(@PathVariable("id") int id) {
        return service.getPassengerData(id);
    }

    @PostMapping("/passengerLogin")
    public Passenger getPassengerData(@RequestBody LoginStaff loginStaff) {
        Passenger p = service.findByEmailAndPassword(loginStaff.getUsername(), loginStaff.getPassword());
        if (p == null) {
            return null;
        }
        return p;
    }

    @PostMapping(value = "/addPassenger", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addPassenger(@ModelAttribute Stafftextdata pass, @RequestParam("image") MultipartFile image) {
        Passenger passenger = new Passenger();
        passenger.setUsername(pass.getUsername());
        passenger.setPassword(pass.getPassword());
        passenger.setGender(pass.getGender());
        passenger.setEmail(pass.getEmail());
        passenger.setPhone(Long.parseLong(pass.getPhone()));
        passenger.setImgname(image.getOriginalFilename());
        passenger.setImgcontenttype(image.getContentType());
        try {
            passenger.setImage(image.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.addPassenger(passenger);
    }

    @PutMapping("/bookFlight")
    public void updatePassenger(@RequestBody FlightAssign request) {
        try {
            service.addFlightToPassenger(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/deletePassenger/{id}")
    public String deletePassenger(@PathVariable("id") int id) {
        Passenger passenger = service.getPassengerData(id);
        if (passenger == null) {
            return "Passenger already deleted or not found";
        }
        if (passenger.getSeats() != null && !passenger.getSeats().isEmpty()) {
            flightService.cancelFlightBooking(passenger.getSeats());
        }
        return service.deletePassenger(id);
    }

    @GetMapping("/passengerSeats/{id}")
    public List<DisplaySeats> getAllPassengerSeats(@PathVariable("id") String id){
        return service.getAllSeatsOfPassenger(id);
    }

    // @PostMapping("/cancelEBooking")
    // public void cancelEconomicBooking(@RequestBody EconomicSeats seat) {
    //     try {
    //         flightService.cancelEconomicFlight(seat);
    //     } catch (Exception e) {
    //         throw e;
    //     }
    // }

    // @PostMapping("/cancelBBooking")
    // public void cancelBusinessBooking(@RequestBody BusinessSeats seat) {
    //     try {
    //         flightService.cancelBusinessFlight(seat);
    //     } catch (Exception e) {
    //         throw e;
    //     }
    // }

}
