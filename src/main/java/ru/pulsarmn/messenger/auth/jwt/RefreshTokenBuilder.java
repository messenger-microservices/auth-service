package ru.pulsarmn.messenger.auth.jwt;

import org.springframework.stereotype.Component;
import ru.pulsarmn.messenger.auth.config.properties.JwtProperties;
import ru.pulsarmn.messenger.auth.domain.AuthUser;
import ru.pulsarmn.messenger.auth.domain.RefreshToken;
import ru.pulsarmn.messenger.auth.util.ByteUtils;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;


@Component
public class RefreshTokenBuilder {

    private final Clock clock;
    private final JwtProperties jwtProperties;
    private final RefreshTokenConverter refreshTokenConverter;

    public RefreshTokenBuilder(Clock clock, JwtProperties jwtProperties, RefreshTokenConverter refreshTokenConverter) {
        this.clock = clock;
        this.jwtProperties = jwtProperties;
        this.refreshTokenConverter = refreshTokenConverter;
    }

    public RefreshTokenContext buildRefreshToken(AuthUser authUser) {
        int refreshTokenLength = jwtProperties.getRefreshTokenLength();
        byte[] rawRefreshTokenBytes = ByteUtils.generateSecureBytes(refreshTokenLength);
        RefreshToken refreshToken = buildRefreshToken(authUser, rawRefreshTokenBytes);
        String encodedRefreshToken = encodeRefreshToken(rawRefreshTokenBytes);
        return new RefreshTokenContext(refreshToken, encodedRefreshToken);
    }

    private RefreshToken buildRefreshToken(AuthUser authUser, byte[] rawRefreshTokenBytes) {
        String hashedRefreshToken = refreshTokenConverter.convertToString(rawRefreshTokenBytes);
        return RefreshToken.builder()
                .tokenHash(hashedRefreshToken)
                .authUser(authUser)
                .expiresAt(getExpirationTime())
                .build();
    }

    private Instant getExpirationTime() {
        return Instant.now(clock).plus(jwtProperties.getRefreshTokenExpirationDays(), ChronoUnit.DAYS);
    }

    private String encodeRefreshToken(byte[] rawRefreshTokenBytes) {
        return Base64.getEncoder().encodeToString(rawRefreshTokenBytes);
    }
}
