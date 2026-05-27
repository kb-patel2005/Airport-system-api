package com.airportSystem.airport_system.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
public class Staffcontroller {

    @DeleteMapping("/staffLogout")
    public String staffLogout() {
        SecurityContextHolder.clearContext();
        return "Logged out successfully";
    }

}
