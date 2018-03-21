/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class JWTHelper {

    private static final String Key = MacProvider.generateKey().toString();

    public static String createJWT(String subject) {

        System.out.println("subject= " + subject);

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        System.out.println("key= " + builder.compact());
        return builder.compact();
    }

    public static Claims parseJWT(String jwt) {
        try {
            if (jwt != null && !jwt.equals("")) {
                //This line will throw an exception if it is not a signed JWS (as expected)
                return Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(Key))
                        .parseClaimsJws(jwt).getBody();
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
