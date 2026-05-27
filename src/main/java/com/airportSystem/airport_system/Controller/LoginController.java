package com.airportSystem.airport_system.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.airportSystem.airport_system.Entities.LoginStaff;
import com.airportSystem.airport_system.Entities.Passenger;
import com.airportSystem.airport_system.Entities.Staff;
import com.airportSystem.airport_system.Entities.Stafftextdata;
import com.airportSystem.airport_system.Service.StaffService;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder PasswordEncoder;

    @Autowired
    private com.airportSystem.airport_system.Config.jwtUtils jwtUtils;

    @Autowired
    private com.airportSystem.airport_system.Service.AirportService service;

    @PostMapping("/staffLogin")
    public HashMap<String, Object> getStaffMember(@RequestBody LoginStaff loginStaff) {
        Staff s = staffService.staffLogin(loginStaff.getUsername(), loginStaff.getPassword());
        if (s == null) {
            return null;
        }
        String token = jwtUtils.generateToken(s.getUsername(), s.getRole());
        HashMap<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("staff", s);
        return response;

    }

    @PostMapping(value = "/staffRegister", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HashMap<String, Object> staffRegister(@ModelAttribute Stafftextdata staffdata,
            @RequestParam("image") MultipartFile image) {
        Staff staff = new Staff();
        staff.setUsername(staffdata.getUsername());
        String encodedPassword =
                PasswordEncoder.encode(
                        staffdata.getPassword());
        staff.setPassword(
                encodedPassword);
        staff.setGender(staffdata.getGender());
        staff.setRole(staffdata.getRole());
        staff.setEmail(staffdata.getEmail());
        staff.setPhone(staffdata.getPhone());
        staff.setImgname(image.getOriginalFilename());
        staff.setImgcontenttype(image.getContentType());
        try {
            staff.setImage(image.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        staffService.staffRegister(staff);
        String token = jwtUtils.generateToken(staff.getUsername(), staff.getRole());

        HashMap<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("staff", staff);
        return response;
    }

    @PostMapping("/passengerLogin")
    public HashMap<String, Object> getPassengerData(@RequestBody LoginStaff loginStaff) {
        Passenger p = service.findByEmailAndPassword(loginStaff.getUsername(), loginStaff.getPassword());
        if (p == null) {
            return null;
        }
        String token = jwtUtils.generateToken(p.getEmail(), "USER");
        HashMap<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("passenger", p);
        return response;
    }

    @PostMapping(value = "/addPassenger", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HashMap<String, Object> addPassenger(@ModelAttribute Stafftextdata pass,
            @RequestParam("image") MultipartFile image) {
        Passenger passenger = new Passenger();
        passenger.setUsername(pass.getUsername());
        passenger.setPassword(PasswordEncoder.encode(pass.getPassword()));
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
        service.addPassenger(passenger);
        String token = jwtUtils.generateToken(
                passenger.getEmail(),
                "USER");
        HashMap<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("passenger", passenger);
        return response;
    }

}
