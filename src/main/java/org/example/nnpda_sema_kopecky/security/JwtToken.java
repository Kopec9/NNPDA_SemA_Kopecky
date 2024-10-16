package org.example.nnpda_sema_kopecky.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtToken {
    @Value("${jwt.secret}")
    private String secretKey;

    public long expiration = 6 * 60 * 60 * 1000; // 6 hours

    public String generateToken(Authentication authentication) {
        return createToken(authentication.getName());
    }

    private String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(makePublicKey())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(makePublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey makePublicKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }
}
