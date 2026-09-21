package ru.pulsarmn.messenger.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pulsarmn.messenger.auth.domain.RefreshToken;
import ru.pulsarmn.messenger.auth.exception.BadCredentialsException;
import ru.pulsarmn.messenger.auth.jwt.RefreshTokenConverter;
import ru.pulsarmn.messenger.auth.repository.RefreshTokenRepository;
import ru.pulsarmn.messenger.auth.util.HashUtils;

import java.time.Clock;
import java.time.Instant;
import java.util.Base64;


@Service
public class RefreshTokenService {

    private final Clock clock;
    private final RefreshTokenConverter refreshTokenConverter;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(Clock clock, RefreshTokenConverter refreshTokenConverter, RefreshTokenRepository refreshTokenRepository) {
        this.clock = clock;
        this.refreshTokenConverter = refreshTokenConverter;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken find(String rawRefreshToken) {
        byte[] rawRefreshTokenBytes = Base64.getUrlDecoder().decode(rawRefreshToken);
        String refreshTokenHash = refreshTokenConverter.convertToString(rawRefreshTokenBytes);
        return refreshTokenRepository.findByTokenHash(refreshTokenHash)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));
    }

    public boolean isExpired(RefreshToken refreshToken) {
        Instant currentTime = Instant.now(clock);
        return refreshToken.getExpiresAt().isBefore(currentTime);
    }

    @Transactional
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }
}
