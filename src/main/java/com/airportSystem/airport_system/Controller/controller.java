package com.airportSystem.airport_system.Controller;

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
import com.airportSystem.airport_system.Dao.FlightAssign;
import com.airportSystem.airport_system.Dao.LoginStaff;
import com.airportSystem.airport_system.Dao.Passenger;
import com.airportSystem.airport_system.Dao.Stafftextdata;
import com.airportSystem.airport_system.ServiceProvider.AirportService;
import com.airportSystem.airport_system.ServiceProvider.Repository;

@RestController
@CrossOrigin("*")
@RequestMapping("api")
public class controller {

    @Autowired
    AirportService service;

    @Autowired
    Repository repository;

    @GetMapping("/welcome/passenger/{id}")
    public Passenger welcome(@PathVariable("id") int id) {
        return service.getPassengerData(id);
    }

    @PostMapping("/passengerLogin")
    public Passenger getPassengerData(@RequestBody LoginStaff loginStaff) {
        Passenger p = service.findByUsernameAndPassword(loginStaff.getUsername(), loginStaff.getPassword());
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

    @PutMapping("/cancelFlight/{id}")
    public String cancelFlight(@PathVariable("id") int id) {
        return service.cancelFlight(id);
    }

    @PutMapping("/addFlight")
    public void updatePassenger(@RequestBody FlightAssign request) {
        try {
            System.out.println(request.getPassengerId());
            Passenger passenger = repository.findByPassportNumber(request.getPassengerId());
            passenger.setFlight(request.getFlight());
            passenger.setSeatno(request.getSeatNo());
            repository.save(passenger);
        } catch (Exception e) {
            throw e;
        }

        // try {
        // service.addFlightId(request.getPassengerId(), request.getFlight(),
        // request.getSeatNo());
        // } catch (Exception e) {
        // System.out.println(e.getMessage());
        // }

    }

    @DeleteMapping("/deletePassenger/{id}")
    public String deletePassenger(@PathVariable int id) {
        return service.deletePassenger(id);
    }

}
