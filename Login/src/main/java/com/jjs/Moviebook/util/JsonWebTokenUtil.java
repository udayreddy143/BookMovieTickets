package com.jjs.Moviebook.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JsonWebTokenUtil {

    private static final String SECRETE="uday1234567898761542456352725272627tgyaujkiokjambsus12345567";

    public  String generateToken(String emailId) {


        //neet to set userinfo
        //token craetion date
        //set expiry
        //set secrete key for furtur validation  (claims)
        return Jwts.builder().setSubject(emailId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*5)).
                signWith(getSingedKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSingedKey(){
      byte[] keyBytes =   Decoders.BASE64.decode(SECRETE);
       return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token){

        return !(Jwts.parserBuilder().setSigningKey(getSingedKey())
                .build().parseClaimsJws(token)
                .getBody().getExpiration().before(new Date()));

    }
}
