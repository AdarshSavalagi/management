package com.sitmng.management.security;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
public class JwtUtil {

    KeyPair keyPair = Jwts.SIG.RS256.keyPair().build(); //or RS384, RS512, PS256, etc...


    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public Boolean validateToken(String jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(jwt);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
