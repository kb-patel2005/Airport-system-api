package com.airportSystem.airport_system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.airportSystem.airport_system.ServiceProvider.jwtUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // @Autowired
    // private AuthenticationManager authManager;
    // @Autowired
    // private jwtUtils jwtUtil;

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody AuthRequest request) {
    //     Authentication authentication = authManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    //     String token = jwtUtil.generateToken(request.getUsername());
    //     return ResponseEntity.ok(new AuthResponse(token));
    // }
}

@Data
class AuthRequest {
    private String username;
    private String password;
}

@Data
@AllArgsConstructor
class AuthResponse {
    private String token;
}
