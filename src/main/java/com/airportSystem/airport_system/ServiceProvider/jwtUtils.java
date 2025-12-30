package com.airportSystem.airport_system.ServiceProvider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;

// @Component
// public class jwtUtils {
    
//     private final String SECRET = "mysecretkey";

//     Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

//     public String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public String extractUsername(String token) {
//         return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
//     }

//     public boolean validateToken(String token, UserDetails userDetails) {
//         return extractUsername(token).equals(userDetails.getUsername()) && 
//                !isTokenExpired(token);
//     }

//     private boolean isTokenExpired(String token) {
//         return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
//                 .getBody().getExpiration().before(new Date());
//     }

// }
