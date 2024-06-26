package com.shop.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    //加密协议
    private static String SECRET = "iuhuhknhuyguygnomnojmoijuhuygtyftyfytguoyguygtf";

    //生成jwt令牌
    public static String generateJWT(Map<String, Object> claims)
    {
        Date expireDate = new Date(System.currentTimeMillis() + 24*3600*1000);
        String token=Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setExpiration(expireDate)
                .compact();
        return token;
    }

    //解析jwt令牌
    public static Claims parseJWT(String token)
    {
        Claims claims=Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
