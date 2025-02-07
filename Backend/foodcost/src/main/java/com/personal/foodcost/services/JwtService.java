package com.personal.foodcost.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final static String SECRET = "6bb1e72d6d9bccb8373ba16e2de8e0bbb52e0356079f0236dbf27f9078f84e35b5f576370eeafecc722d7a52ea74801ec27f025684d389c40a0ea9a0f2a86b513f95539397433dc842c878b4b2e092c42b01d1a97af718a1dff2844db7538dd19cccd2046d0464a1288844e39da668f0d2a324ae0d3dd61dc40e099c07d595226ab028c5bb16e389ae2ca53a9ed7245caf81cf8ba3990f397da21e24d787b75fe607ebaf18b80e7c375a0f8adead95f4bd44d0ee91d3b8f8bb836420ecf8ad12cf3ef8c38046f9b33f6584e1649f49e288dd89a9f3ab83f3b5972665da7bf4c2224e4e81d475f015c7f99bab4456d137130899c7d1f368f34dc29058f5dc8c3a";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decodeKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodeKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims (String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }
}
