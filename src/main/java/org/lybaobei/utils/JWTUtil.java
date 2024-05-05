package org.lybaobei.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.compression.GzipCompressionAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author nommpp
 * @date 2024/5/4 0004
 */
public class JWTUtil {
    private static long tokenExpiration = 365*24*60*60*1000;
    private static String tokenSignKey = "W7OL7qHQgxBLtpaI5QbYIMtY5JiZs5sC=XWBOjKsjU4!";
    
    public static String createToken(String userId,String userName){
        String token = Jwts.builder()
                .subject("AUTH-USER")
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId",userId)
                .claim("userName",userName)
                .signWith(generalKeyByDecoders())
                .compressWith(new GzipCompressionAlgorithm())
                .compact();
        return token;
    }
    
    public static SecretKey generalKeyByDecoders(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenSignKey));
    }
    
    public static String getUserId(String token){
        Claims claimsJws = Jwts.parser()
                .verifyWith(generalKeyByDecoders())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String userId = claimsJws.get("userId", String.class);
        return userId;
    }
    
    public static String getUserName(String token){
        Claims claimsJws = Jwts.parser()
                .verifyWith(generalKeyByDecoders())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String userName = claimsJws.get("userName", String.class);
        
        return userName;
    }
    
    public static Claims getSubject(String token){
        return Jwts.parser()
                .verifyWith(generalKeyByDecoders())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    public static void main(String[] args) {
        String token = createToken("7ee51a6e91dd4ac38a100b182dcf4d59","admin");
        System.out.println(token);
        System.out.println(getUserId(token));
        System.out.println(getUserName(token));
        
        Claims claims = getSubject(token);
        System.out.println(claims.getSubject());
        System.out.println(claims.getId());
    }
}
