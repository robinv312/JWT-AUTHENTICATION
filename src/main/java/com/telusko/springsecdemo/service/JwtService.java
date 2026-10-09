package com.telusko.springsecdemo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {

    private String secretKey;
    public JwtService() {
        secretKey = generateSecretKey();
    }

    public String generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGen.generateKey();
            System.out.println("Secret Key : " + secretKey.toString());
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(); // Claims can include custom data (e.g., roles, permissions)
        claims.put("username", username); // Adding custom claim

        return Jwts.builder()
                .setClaims(claims) // Add claims to the token
                .setSubject(username) // Set the subject (e.g., the username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Current time as issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3)) // Token expiration time (3hours)
 .signWith( getKey(),SignatureAlgorithm.HS256) // Sign the token with the secret key
                .compact(); // Generate the token
    }
}
