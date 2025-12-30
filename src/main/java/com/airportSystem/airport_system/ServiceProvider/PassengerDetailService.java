package com.airportSystem.airport_system.ServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.Passenger;

// @Service
// public class PassengerDetailService implements UserDetailsService {

//     @Autowired
//     private Repository repository;

//     @Override
//     public UserDetails loadUserByUsername(String username)
//             throws org.springframework.security.core.userdetails.UsernameNotFoundException {
//         Passenger passenger = repository.findByUsername(username)
//                 .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found"));

//         return org.springframework.security.core.userdetails.User
//                 .withUsername(passenger.getUsername())
//                 .password(passenger.getPassword())
//                 .accountExpired(false)
//                 .authorities("USER")
//                 .build();
//     }

// }
