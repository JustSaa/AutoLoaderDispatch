package ru.autoloader.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JWTUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Генерируем секретный ключ
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 часа

    // Генерация JWT токена
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Извлечение username из токена
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Извлечение конкретного claim'а
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Парсим все claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Проверка токена на валидность
    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return (username.equals(extractedUsername) && !isTokenExpired(token));
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    // Проверка срока действия токена
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}