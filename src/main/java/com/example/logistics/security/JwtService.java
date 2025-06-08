package com.example.logistics.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.logistics.entity.User;
import com.example.logistics.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepository;
    @Value("${security.secret.key}")
    private String secretKey;
    private final UserRepository userRepo;

    public String generateToken(User user){
        ZonedDateTime now = ZonedDateTime.now();
        return JWT.create()
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(now.toInstant())
                .withExpiresAt(now.plusSeconds(100000000).toInstant())
                .sign(getAlgorithm());
    }
    public User verifyToken(String token){
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getClaim("email").asString(); // или переименуй в phoneNumber в generateToken
        return userRepository.findUserByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found with email: " + email)
        );
    }

    public Algorithm getAlgorithm(){
        return Algorithm.HMAC256(secretKey);
    }

    // SecurityContextHolder

}