package com.airportSystem.airport_system.ServiceProvider;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends org.springframework.web.filter.OncePerRequestFilter {

    // @Autowired
    // private jwtUtils jwtUtils;

    // @Autowired
    // private UserDetailsService PassengerDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,FilterChain filterChain)
            throws java.io.IOException, ServletException {
        
        final String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            // username = jwtUtils.extractUsername(token);
        }
            
        // if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        //     UserDetails userDetails = 
        //         PassengerDetailService.loadUserByUsername(username);
            
        //     if (jwtUtils.validateToken(token, userDetails)) {
        //         UsernamePasswordAuthenticationToken authToken =
        //             new UsernamePasswordAuthenticationToken(
        //                 userDetails, null, userDetails.getAuthorities());
                
        //         authToken.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource().buildDetails(request));
                
        //         SecurityContextHolder.getContext().setAuthentication(authToken);
        //     }
        // }

        filterChain.doFilter(request, response);
    }
    
}
