package com.example.logistics.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.logistics.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.logistics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.secret.key}")
    private String secretKey;

    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public String generateToken(User user) {
        ZonedDateTime now = ZonedDateTime.now();
        String token = JWT.create()
                .withClaim("id", user.getId())
                .withClaim("phoneNumber", user.getPhoneNumber())
                .withClaim("role", user.getRole().ordinal())
                .withIssuedAt(Date.from(now.toInstant()))
                .withExpiresAt(Date.from(now.plusSeconds(100000000).toInstant()))
                .sign(Algorithm.HMAC256(secretKey));
        System.out.println("Generated JWT token: " + token);
        return token;
    }
    public User verifyToken(String token) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String phoneNumber = decodedJWT.getClaim("phoneNumber").asString();
        return userRepository.findUserByPhoneNumber(phoneNumber).orElseThrow(
                () -> new RuntimeException("User not found with phone number: " + phoneNumber)
        );
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}