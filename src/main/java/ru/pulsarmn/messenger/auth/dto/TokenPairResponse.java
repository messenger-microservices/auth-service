package ru.pulsarmn.messenger.auth.dto;


public record TokenPairResponse(String accessToken, String refreshToken) {
}
