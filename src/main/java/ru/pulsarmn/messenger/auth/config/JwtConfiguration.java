package ru.pulsarmn.messenger.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.pulsarmn.messenger.auth.config.properties.JwtProperties;

import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {

    private final JwtProperties jwtProperties;

    private static final String ALGORITHM = "EC";

    public JwtConfiguration(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    ECPrivateKey accessTokenPrivateKey() throws Exception {
        String rawPrivateKey = jwtProperties.getPrivateKey();
        byte[] privateKeyBytes = Base64.getDecoder().decode(rawPrivateKey);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return (ECPrivateKey) keyFactory.generatePrivate(privateKeySpec);
    }
}
