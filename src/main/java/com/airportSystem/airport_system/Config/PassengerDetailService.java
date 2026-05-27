package com.airportSystem.airport_system.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.PassengerRepository;
import com.airportSystem.airport_system.Entities.Passenger;

@Service
public class PassengerDetailService implements UserDetailsService {

    @Autowired
    private PassengerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws org.springframework.security.core.userdetails.UsernameNotFoundException {
        Passenger passenger = repository.findByEmail(username)
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(passenger.getEmail())
                .password(passenger.getPassword())
                .accountExpired(false)
                .authorities("USER")
                .build();
    }

}
