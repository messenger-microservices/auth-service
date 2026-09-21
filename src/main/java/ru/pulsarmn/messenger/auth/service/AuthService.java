package ru.pulsarmn.messenger.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import ru.pulsarmn.messenger.auth.jwt.TokenPairFactory;
import ru.pulsarmn.messenger.auth.domain.AuthUser;
import ru.pulsarmn.messenger.auth.dto.RegistrationRequest;
import ru.pulsarmn.messenger.auth.dto.TokenPairResponse;
import ru.pulsarmn.messenger.auth.dto.UserCreateRequest;
import ru.pulsarmn.messenger.auth.exception.BadCredentialsException;
import ru.pulsarmn.messenger.auth.exception.RegistrationException;
import ru.pulsarmn.messenger.auth.exception.ServiceUnavailableException;
import ru.pulsarmn.messenger.auth.mapper.AuthUserMapper;
import ru.pulsarmn.messenger.auth.repository.AuthUserRepository;


@Service
public class AuthService {

    private final AuthUserMapper authUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final RestClient userServiceRestClient;
    private final AuthUserRepository authUserRepository;
    private final TransactionTemplate transactionTemplate;
    private final TokenPairFactory tokenPairFactory;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(AuthUserMapper authUserMapper, PasswordEncoder passwordEncoder, RestClient userServiceRestClient, AuthUserRepository authUserRepository, TransactionTemplate transactionTemplate, TokenPairFactory tokenPairFactory) {
        this.authUserMapper = authUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.userServiceRestClient = userServiceRestClient;
        this.authUserRepository = authUserRepository;
        this.transactionTemplate = transactionTemplate;
        this.tokenPairFactory = tokenPairFactory;
    }

    public TokenPairResponse register(RegistrationRequest request) {
        validatePasswordsMatch(request);

        AuthUser authUser = mapToAuthUser(request);
        AuthUser savedAuthUser = transactionTemplate.execute(_ -> authUserRepository.save(authUser));
        UserCreateRequest userCreateRequest = authUserMapper.mapToUserCreateRequest(savedAuthUser);

        try {
            userServiceRestClient.post()
                    .uri("/api/v1/users")
                    .body(userCreateRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException ex) {
            compensateRegistration(authUser);
            throw new RegistrationException("Invalid user data: " + ex.getMessage());
        } catch (HttpServerErrorException ex) {
            compensateRegistration(authUser);
            throw new ServiceUnavailableException("User service is unavailable");
        }
        return tokenPairFactory.createTokenPair(savedAuthUser);
    }

    private void compensateRegistration(AuthUser authUser) {
        try {
            transactionTemplate.executeWithoutResult(status -> authUserRepository.delete(authUser));
        } catch (Exception ex) {
            log.error("Failed to rollback creation of AuthUser with id: {}", authUser.getId());
        }
    }

    private void validatePasswordsMatch(RegistrationRequest request) {
        if (!passwordsMatch(request)) {
            throw new BadCredentialsException("Passwords don't match");
        }
    }

    private boolean passwordsMatch(RegistrationRequest request) {
        return (request.password()).equals(request.passwordConfirmation());
    }

    private AuthUser mapToAuthUser(RegistrationRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        return authUserMapper.map(request, encodedPassword);
    }
}
