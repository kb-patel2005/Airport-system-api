package com.airportSystem.airport_system.Dao;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class Stafftextdata {

    private String password;
    private String username;
    private String gender;
    private String role;
    private String phone;
    private String email;
}
