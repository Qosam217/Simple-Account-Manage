package com.demo.simple_account.services;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    private String secretKey = "";
    
    @Value("${jwt.access.token.expiration}")
    private long accessTokenValidity;

    @Value("${jwt.refresh.token.expiration}")
    private long refreshTokenValidity;

    public JWTService(){
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keygen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateAccessToken(String username){
        return generateToken(username, accessTokenValidity);
    }

    public String generateRefreshToken(String username){
        return generateToken(username, refreshTokenValidity);
    }

    public String generateToken(String username, long validity){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                    .claims()
                    .add(claims)
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + validity))
                    .and()
                    .signWith(getKey())
                    .compact();
    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);

    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
}
