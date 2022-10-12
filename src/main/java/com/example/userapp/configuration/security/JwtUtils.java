package com.example.userapp.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;


@Component
public class JwtUtils {
    private final Key key;
    private final Long expiration;


    public JwtUtils(@Value("${userapp.jwtSecret}") String secret, @Value("${userapp.jwtExpiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String encode(String sub) {
        if (sub == null || sub.equals("")) {
            return null;
        }
        Instant exp = Instant.now();
        return Jwts.builder()
                .setSubject(sub)
                .setIssuedAt(new Date(exp.toEpochMilli()))
                .setExpiration(new Date(exp.toEpochMilli() + expiration * 1000))
                .signWith(key)
                .compact();
    }

    public String resolveToken(ServletRequest request) {
        // sometimes we want Bearer in front or Token
        return ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
    }

    public boolean validateToken(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            Instant now = Instant.now();
            Date exp = claims.getExpiration();
            return exp.after(Date.from(now));
        } catch (JwtException e) {
            return false;
        }
    }

    public String getSub(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}
