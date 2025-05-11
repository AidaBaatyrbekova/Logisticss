package com.example.logistics.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.logistics.entity.User;
import com.example.logistics.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.secret.key}")
    private String secretKey;  // Жүктөлгөн сыр сөз

    private final UserRepository userRepository;  // Жанакердин репозиториясы

    // Токенди түзүү методун түзөбүз
    public String generateToken(User user) {
        ZonedDateTime now = ZonedDateTime.now();
        return JWT.create()
                .withClaim("id", user.getId())
                .withClaim("phoneNumber", user.getPhoneNumber())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(now.toInstant())
                .withExpiresAt(now.plusSeconds(100000000).toInstant()) // Токендин мөөнөтү
                .sign(getAlgorithm());
    }

    // Токенди текшерүү
    public User verifyToken(String token) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String phoneNumber = decodedJWT.getClaim("phoneNumber").asString();
        return userRepository.findUserByPhoneNumber(phoneNumber).orElseThrow(
                () -> new RuntimeException("User not found with phone number: " + phoneNumber)
        );
    }

    // Алгоритмди түзүү
    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}