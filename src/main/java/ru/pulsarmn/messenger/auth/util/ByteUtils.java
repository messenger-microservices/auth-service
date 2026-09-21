package ru.pulsarmn.messenger.auth.util;

import java.security.SecureRandom;


public class ByteUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static byte[] generateSecureBytes(int length) {
        return generateSecureBytes(length, SECURE_RANDOM);
    }

    public static byte[] generateSecureBytes(int length, SecureRandom secureRandom) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must be non-negative");
        }
        byte[] bytes = new byte[length];
        secureRandom.nextBytes(bytes);
        return bytes;
    }
}
