package ru.pulsarmn.messenger.auth.config.properties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties("app.jwt")
public class JwtProperties {

    @NotBlank
    private String privateKey;

    @Min(5)
    @Max(60)
    private int accessTokenExpirationMinutes = 15;

    @Min(30)
    private int refreshTokenExpirationDays = 30;

    @Min(32)
    @Max(64)
    private int refreshTokenLength = 32;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public int getAccessTokenExpirationMinutes() {
        return accessTokenExpirationMinutes;
    }

    public void setAccessTokenExpirationMinutes(int accessTokenExpirationMinutes) {
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
    }

    public int getRefreshTokenLength() {
        return refreshTokenLength;
    }

    public void setRefreshTokenLength(int refreshTokenLength) {
        this.refreshTokenLength = refreshTokenLength;
    }

    public int getRefreshTokenExpirationDays() {
        return refreshTokenExpirationDays;
    }

    public void setRefreshTokenExpirationDays(int refreshTokenExpirationDays) {
        this.refreshTokenExpirationDays = refreshTokenExpirationDays;
    }
}
