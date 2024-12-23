package com.nourayni.facturation.jwt;

import com.nourayni.facturation.security.AuthUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private  String keySecret;

    private static final long EXPIRATION_TIME_TOKEN = 15 * 60 * 1000; // 15 minutes
    private static final long EXPIRATION_TIME_REFRESH_TOKEN = 2 * 24 * 60 * 60 * 1000; // 2 jours


    private SecretKey getSecretKey(){
        byte[] keyBytes = Base64.getUrlDecoder().decode(keySecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty");
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        if (validateToken(token)) {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } else {
            logger.error("JWT Token cannot be trusted");
            return null;
        }
    }

    public String getUserIdFromToken(String token) {
        if (validateToken(token)) {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("id", String.class);
        } else {
            logger.error("token non valide");
            return null;
        }
    }

    public String generateToken(Authentication authentication){
        AuthUser userPrincipal = (AuthUser) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("id", userPrincipal.getIdAuth())
                .claim("roles", userPrincipal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_TOKEN))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String refreshToken(Authentication authentication){
        AuthUser userPrincipal = (AuthUser) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("id", userPrincipal.getIdAuth())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESH_TOKEN))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }
}

