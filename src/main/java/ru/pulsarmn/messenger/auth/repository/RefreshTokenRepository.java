package ru.pulsarmn.messenger.auth.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pulsarmn.messenger.auth.domain.RefreshToken;

import java.util.UUID;


public interface RefreshTokenRepository extends JpaRepository<@NonNull RefreshToken, @NonNull UUID> {
}
