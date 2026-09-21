package ru.pulsarmn.messenger.auth.jwt;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.pulsarmn.messenger.auth.domain.AuthUser;
import ru.pulsarmn.messenger.auth.repository.RefreshTokenRepository;


@Component
public class RefreshTokenFactory {

    private final RefreshTokenBuilder refreshTokenBuilder;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenFactory(RefreshTokenBuilder refreshTokenBuilder, RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenBuilder = refreshTokenBuilder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public String createRefreshToken(AuthUser authUser) {
        RefreshTokenContext refreshTokenContext = refreshTokenBuilder.buildRefreshToken(authUser);
        refreshTokenRepository.save(refreshTokenContext.refreshToken());
        return refreshTokenContext.rawRefreshToken();
    }
}
