package com.airportSystem.airport_system.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends org.springframework.web.filter.OncePerRequestFilter {

    @Autowired
    private jwtUtils jwtUtils;

    @Autowired
    private PassengerDetailService PassengerDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws IOException,ServletException, java.io.IOException {

        // IMPORTANT FOR CORS
        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request,response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authorizationHeader != null &&authorizationHeader.startsWith("Bearer ")) {

            token = authorizationHeader.substring(7);

            try {
                username = jwtUtils.extractUsername(token);
                System.out.println("USERNAME: " + username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =PassengerDetailService.loadUserByUsername(username);

            if (jwtUtils.validateToken(token,userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);
    }

}
