package com.learn.blogging.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper{
    
    public static  final long JWT_TOKEN_VALIDITY=60*60*5;

     private String secret="SDSJNFKJDSiruahfjisdbvdsiuhSFDUHBUDSYBf3857834";



    public JwtTokenHelper() throws FileNotFoundException, KeyStoreException {
    }

    public String getUsernameFromToken(String token)
    {
        return  getClaimFromToken(token,Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token)
    {
        return  getClaimFromToken(token,Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T>claimsResolver) {

        final  Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }

    public Claims getAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public  Boolean isTokenExpired(String token)
    {
        final Date expiration =getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String  generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }

    public String doGenerateToken(Map<String, Object> claims, String username) {

        return Jwts.builder().setClaims(claims).setSubject(username).
                setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public Boolean validateToken(String token,UserDetails userDetails)
    {
        final String username=getUsernameFromToken(token);
        if(username!=null && !isTokenExpired(token))
            return true;
        return false;
    }





}
