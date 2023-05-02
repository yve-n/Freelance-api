package com.cda.freely.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
@Configuration
public class jwtKeyProvider {
    @Bean
    public static SecretKey generateHS512Key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

}
