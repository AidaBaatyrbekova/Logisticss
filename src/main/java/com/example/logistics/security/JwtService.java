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
    private static final String SECRET_KEY = "yourSecretKey"; // Токен үчүн сырыңыз
    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public String generateToken(User user) {
        ZonedDateTime now = ZonedDateTime.now();
        return JWT.create()
                .withClaim("id", user.getId())  // Колдонуучунун idсин кошо алабыз
                .withClaim("phoneNumber", user.getPhoneNumber())  // Колдонуучунун телефон номерин кошо алабыз
                .withClaim("role", user.getRole().ordinal())  // Колдонуучунун ролун кошо алабыз (role бул String болсо)
                .withIssuedAt(Date.from(now.toInstant()))  // Токен жасалган убакыт
                .withExpiresAt(Date.from(now.plusSeconds(100000000).toInstant()))  // Токендин мөөнөтү
                .sign(Algorithm.HMAC256(SECRET_KEY));  // Сыр сөз менен токенди колдонуңуз
    }

    // Токенди текшерүү
    public User verifyToken(String token) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String phoneNumber = decodedJWT.getClaim("phoneNumber").asString(); // или переименуй в phoneNumber в generateToken
        return userRepository.findUserByPhoneNumber(phoneNumber).orElseThrow(
                () -> new RuntimeException("User not found with phone number: " + phoneNumber)
        );
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }
}