package com.groupT.Smart.Campus.Services.Portal.service.Interface;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    public String generateToken(UserDetails userDetails);
    public String createToken(Map<String, Object> claims, String subject);
    public Boolean isTokenExpired(String token);
    public Claims extractAllClaims(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    public Date extractExpiration(String token);
    public String extractRole(String token);
    public String extractUsername(String token);
    public Boolean validateToken(String token, UserDetails userDetails);

}
