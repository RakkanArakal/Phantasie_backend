//package com.phantasie.demo.utils;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class JwtUtil {
//
//    private final static String base64Secret = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";
//
//    public static String createJWT(String username) throws UnsupportedEncodingException {
//        Date date = new Date();
//        Calendar now = Calendar.getInstance();
//        now.add(Calendar.MINUTE,1);
//        Date expireDate = now.getTime();
//
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("alg","HS256");
//        map.put("typ","JWT");
//
//        String token = JWT.create()
//                .withHeader(map)
//                .withClaim("name",username)
//                .withExpiresAt(expireDate)
//                .withIssuedAt(date)
//                .sign(Algorithm.HMAC256(base64Secret));
//
//        return token;
//    }
//
//    public static  Map<String, Claim> verify(String token) throws UnsupportedEncodingException {
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(base64Secret))
//                .build();
//        DecodedJWT jwt = null;
//
//        try{
//            jwt = verifier.verify(token);
//        }catch (Exception e){
//            throw  new RuntimeException("凭证已过期");
//        }
//        return jwt.getClaims();
//
//    }
//
//}
