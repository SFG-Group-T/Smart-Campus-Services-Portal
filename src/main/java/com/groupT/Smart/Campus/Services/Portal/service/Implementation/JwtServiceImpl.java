package com.groupT.Smart.Campus.Services.Portal.service.Implementation;

import com.groupT.Smart.Campus.Services.Portal.service.Interface.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "ZW5lbXluZXh0Y2xvc2VybG9zc2F0cGVuc3dpbW1pbmdub3J0aGdvbGRmb3Jnb3R0ZW4=";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        claims.put("role", role);
        claims.put("username", userDetails.getUsername());
        return createToken(claims, userDetails.getUsername());
    }

    @Override
    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
