package com.example.logistics.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.logistics.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        // Токенди текшерүү
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // "Bearer " деген сөздү алып салуу
            try {
                User user = jwtService.verifyToken(token);
                if (user != null) {
                    // Колдонуучу туура болсо, аны SecurityContextке кошобуз
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(user.getPhoneNumber(), null, user.getAuthorities())
                    );
                }
            } catch (Exception exception) {
                // Токен туура эмес болсо, 401 статус кодун кайтарып жиберебиз
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Unauthorized if token invalid
            }
        }
        // Андан кийин, суранышты башка фильтрлерге өткөрөбүз
        filterChain.doFilter(request, response);
    }
}