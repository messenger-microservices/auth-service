package ru.pulsarmn.messenger.auth.mapper;

import org.springframework.stereotype.Component;
import ru.pulsarmn.messenger.auth.domain.AuthUser;
import ru.pulsarmn.messenger.auth.dto.RegistrationRequest;
import ru.pulsarmn.messenger.auth.dto.UserCreateRequest;


@Component
public class AuthUserMapper {

    public AuthUser map(RegistrationRequest request, String encodedPassword) {
        return AuthUser.builder()
                .username(request.username())
                .passwordHash(encodedPassword)
                .build();
    }

    public UserCreateRequest mapToUserCreateRequest(AuthUser authUser) {
        return UserCreateRequest.builder()
                .id(authUser.getId())
                .username(authUser.getUsername())
                .phoneNumber(authUser.getPhoneNumber())
                .displayName("Default Display Name")
                .build();
    }
}
