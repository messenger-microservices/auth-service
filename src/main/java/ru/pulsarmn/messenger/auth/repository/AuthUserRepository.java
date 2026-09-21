package ru.pulsarmn.messenger.auth.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pulsarmn.messenger.auth.domain.AuthUser;

import java.util.UUID;


public interface AuthUserRepository extends JpaRepository<@NonNull AuthUser, @NonNull UUID> {
}
