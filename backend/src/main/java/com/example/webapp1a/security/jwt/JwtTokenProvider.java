package com.example.webapp1a.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Component
public class JwtTokenProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch(SignatureException ex){
            LOG.debug("Invalid JWT Signature");
        } catch(MalformedJwtException ex){
            LOG.debug("Invalid JWT token");
        } catch(ExpiredJwtException ex){
            LOG.debug("expired JWT token");
        } catch(UnsupportedJwtException ex){
            LOG.debug("Unsupported JWT exception");
        } catch(IllegalArgumentException ex){
            LOG.debug("JWT claims string is empty");
        }
        return false;
    }

    public Token generateToken(UserDetails user){
        
    }
    
}
