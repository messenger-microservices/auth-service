package ru.pulsarmn.messenger.auth.jwt;

import org.springframework.stereotype.Component;
import ru.pulsarmn.messenger.auth.domain.AuthUser;
import ru.pulsarmn.messenger.auth.dto.response.TokenPairResponse;


@Component
public class TokenPairFactory {

    private final JwtClaimsFactory jwtClaimsFactory;
    private final AccessTokenFactory accessTokenFactory;
    private final RefreshTokenFactory refreshTokenFactory;

    public TokenPairFactory(JwtClaimsFactory jwtClaimsFactory, AccessTokenFactory accessTokenFactory, RefreshTokenFactory refreshTokenFactory) {
        this.jwtClaimsFactory = jwtClaimsFactory;
        this.accessTokenFactory = accessTokenFactory;
        this.refreshTokenFactory = refreshTokenFactory;
    }

    public TokenPairResponse createTokenPair(AuthUser authUser) {
        JwtClaims jwtClaims = jwtClaimsFactory.createJwtClaims(authUser);
        String accessToken = accessTokenFactory.createAccessToken(jwtClaims);
        String refreshToken = refreshTokenFactory.createRefreshToken(authUser);
        return new TokenPairResponse(accessToken, refreshToken);
    }
}
