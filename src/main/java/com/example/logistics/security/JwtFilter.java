package com.example.logistics.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.logistics.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;  // JwtService объектин инъекция кылуу

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");  // Authorization башталышындагы хедерди алуу
        if (header != null && header.startsWith("Bearer ")) {  // Токен текшерүү
            String token = header.substring(7);  // Токенди алып алуу (Bearer токен)
            try {
                User user = jwtService.verifyToken(token);  // Токенди текшерүү
                if (user != null) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities())  // Аутентификацияны орнотуу
                    );
                }
            } catch (JWTVerificationException exception) {
                throw new RuntimeException(exception);  // Токен туура эмес болсо ката чыгаруу
            }
        }
        filterChain.doFilter(request, response);  // Фильтр аркылуу өтүү
    }
}
