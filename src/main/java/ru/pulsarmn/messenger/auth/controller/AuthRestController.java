package ru.pulsarmn.messenger.auth.controller;

import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pulsarmn.messenger.auth.dto.RegistrationRequest;
import ru.pulsarmn.messenger.auth.dto.TokenPairResponse;
import ru.pulsarmn.messenger.auth.service.AuthService;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    ResponseEntity<@NonNull TokenPairResponse> register(@Validated @RequestBody RegistrationRequest request) {
        TokenPairResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
}
