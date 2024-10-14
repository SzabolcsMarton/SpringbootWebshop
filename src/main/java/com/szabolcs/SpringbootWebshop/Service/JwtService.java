package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Model.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private String expiration;

    // Token generálás
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof SecurityUser securityUser) {
            claims.put("email", securityUser.getUsername());
            claims.put("roles", securityUser.getAuthorities());
        }
        return createToken(claims, userDetails.getUsername());
    }

    // Token létrehozása, expiration idő beállításával
    private String createToken(Map<String, Object> claims, String subject) {
        //Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Key key = getSignInKey();
        long expir = Long.parseLong(expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expir)) // 10 óra
                .signWith(key, SignatureAlgorithm.HS256)
               // .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Token validálása
    public boolean validateToken(String token, UserDetails userDetails) {
        System.out.println("token validálásnál: " + token);
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Kivonja a felhasználónevet (itt e-mail címként használjuk)
    public String extractUsername(String token) {
        String username =  extractClaim(token, Claims::getSubject);
        System.out.println("extraxt username: " + username);
        return username;
    }

    // Kivonja a tokenből a megadott claim-et
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("extract claim");
        final Claims claims = extractAllClaims(token);
        System.out.println("extracted claims: " + claims.toString());
        return claimsResolver.apply(claims);
    }

//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//    }

    public Claims extractAllClaims(String token) {
        System.out.println(token);
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    // Ellenőrzi, hogy lejárt-e a token
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Kivonja a lejárati időt
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

