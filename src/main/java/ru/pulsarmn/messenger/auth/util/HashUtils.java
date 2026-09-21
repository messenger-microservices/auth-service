package ru.pulsarmn.messenger.auth.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public abstract class HashUtils {

    private static final String SHA_256 = "SHA-256";

    public static byte[] sha256Hash(String data) {
        byte[] rawBytes = data.getBytes(StandardCharsets.UTF_8);
        return sha256Hash(rawBytes);
    }

    public static byte[] sha256Hash(byte[] rawBytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
            return messageDigest.digest(rawBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Critical error: %s algorithm is missing".formatted(SHA_256));
        }
    }
}
