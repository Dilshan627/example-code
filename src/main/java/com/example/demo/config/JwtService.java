package com.example.demo.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  public String extractUsername(String token) throws ExpiredJwtException{
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    if (token == null || token.isEmpty()) {
      throw new IllegalArgumentException("Token cannot be null or empty");
    }

    final Claims claims = extractAllClaims(token);
    if (claims == null) {
      throw new IllegalArgumentException("Failed to extract claims from token");
    }

    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String generateRefreshToken(
      UserDetails userDetails
  ) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  private String buildToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails,
          long expiration
  ) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    try {
      return Jwts
              .parserBuilder()
              .setSigningKey(getSignInKey())
//              .setAllowedClockSkewSeconds(60)
              .build()
              .parseClaimsJws(token)
              .getBody();
    } catch (ExpiredJwtException e) {
      System.err.println("JWT has expired: " + e.getMessage());
      throw e;
    } catch (UnsupportedJwtException e) {
      System.err.println("Unsupported JWT: " + e.getMessage());
      throw e;
    } catch (MalformedJwtException e) {
      System.err.println("Malformed JWT: " + e.getMessage());
      throw e;
    } catch (SignatureException e) {
      System.err.println("Invalid JWT signature: " + e.getMessage());
      throw e;
    } catch (IllegalArgumentException e) {
      System.err.println("JWT token is null or empty: " + e.getMessage());
      throw e;
    } catch (Exception e) {
      System.err.println("JWT parsing error: " + e.getMessage());
      throw new IllegalArgumentException("Failed to extract claims from token", e);
    }
  }


  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
