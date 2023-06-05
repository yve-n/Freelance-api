package com.cda.freely.config;

import com.cda.freely.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    @Autowired
    private SecretKey secretKey;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);


    /**
     *
     * @param token
     * @return claims : an object containing getter and setter necessary for the token body
     */
    public Claims getTokenBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     *
     * @param authentication
     * @return token :  to identify user
     */
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        //ajout du role du user dans le corps du token
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("roles", userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));

        /**[mise en place d'un système d'invalidation de token en cas de déconnexion]
         *  ajout du numéro de token dans les données du jwt
         *  */
        tokenData.put("tokenNumber", userPrincipal.getTokenNumber());
        return Jwts.builder()
                .setClaims(tokenData)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey,SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     *
     * @param token token previously generated
     * @return subject (userName for User)
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            String messageError = ex.getMessage();
            System.out.println(messageError);
            return false;
        }
    }

    private Boolean isTokenPassedExpirationDate(String token){
        return getTokenBody(token)
                .getExpiration()
                .after(new Date());
    }
    public Boolean isTokenValid(String token , UserPrincipal userPrincipal){
        Claims claims = getTokenBody(token);
        boolean validUser = claims.getSubject().equals(userPrincipal.getUsername());

        /**[système d'invalidation de token]
         * vérifier que le numéro de token est identique à celui de la table
         */
        boolean validTokenNumber = claims
                .get("tokenNumber")
                .equals(userPrincipal.getTokenNumber());
        return validUser && validTokenNumber;
    }

    public String extractUserNameFromToken(String token ){
        logger.warn("username claims ----------- {}",token);
        if (token != null && token.startsWith("Bearer ")) {
            String bearerToken =  token.substring(7);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(bearerToken)
                    .getBody();
            return claims.getSubject();
        }
        return null;
    }


}
