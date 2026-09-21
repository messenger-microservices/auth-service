package ru.pulsarmn.messenger.auth.jwt;

import ru.pulsarmn.messenger.auth.domain.RefreshToken;


public record RefreshTokenContext(RefreshToken refreshToken, String rawRefreshToken) {
}
