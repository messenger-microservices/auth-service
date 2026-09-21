package ru.pulsarmn.messenger.auth.jwt;

import org.springframework.stereotype.Component;
import ru.pulsarmn.messenger.auth.config.properties.JwtProperties;
import ru.pulsarmn.messenger.auth.domain.AuthUser;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Component
public class JwtClaimsFactory {

    private final Clock clock;
    private final JwtProperties jwtProperties;

    private static final String USERNAME_CLAIM = "username";

    public JwtClaimsFactory(Clock clock, JwtProperties jwtProperties) {
        this.clock = clock;
        this.jwtProperties = jwtProperties;
    }

    public JwtClaims createJwtClaims(AuthUser authUser) {
        return JwtClaims.builder()
                .subject(authUser.getId().toString())
                .claim(USERNAME_CLAIM, authUser.getUsername())
                .expirationTime(getExpirationTime())
                .issueTime(getIssueTime())
                .build();
    }

    private Instant getExpirationTime() {
        return Instant.now(clock).plus(jwtProperties.getAccessTokenExpirationMinutes(), ChronoUnit.MINUTES);
    }

    private Instant getIssueTime() {
        return Instant.now(clock);
    }
}
