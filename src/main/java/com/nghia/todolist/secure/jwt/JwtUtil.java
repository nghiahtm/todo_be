package com.nghia.todolist.secure.jwt;

import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


@Component
public class JwtUtil {
    @Value("${auth.jwt.secret}")
    private String jwtSecret;
    @Value("${auth.jwt.expiration}")
    private long expirationAccessToken;
    @Value("${auth.jwt.expiration_ref_token}")
    private long expirationRefreshToken;
    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    public String generateAccessToken(UserDtoNoPassword user){
       try {
           return Jwts.builder().setSubject(user.getEmail())
                   .claim("fullName", user.getName())
                   .claim("role",user.getRole())
                   .claim("email",user.getEmail())
                   .claim("id",user.getId())
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(new Date().getTime()+(expirationAccessToken * 1000)))
                   .signWith(getSignKey(),SignatureAlgorithm.HS256)
                   .compact();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }
    public String generateRefreshToken(String username){
        try {
            return Jwts.builder().setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+(expirationRefreshToken * 1000)))
                    .signWith(getSignKey(),SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes()) // nếu secret là String
                    .build()
                    .parseClaimsJws(authToken);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
