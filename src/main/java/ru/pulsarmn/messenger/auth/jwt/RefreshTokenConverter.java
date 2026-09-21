package ru.pulsarmn.messenger.auth.jwt;

import org.springframework.stereotype.Component;
import ru.pulsarmn.messenger.auth.util.HashUtils;

import java.util.Base64;


@Component
public class RefreshTokenConverter {

    public String convertToString(byte[] rawRefreshTokenBytes) {
        byte[] hashedRefreshTokenBytes = HashUtils.sha256Hash(rawRefreshTokenBytes);
        return Base64.getEncoder().encodeToString(hashedRefreshTokenBytes);
    }
}
