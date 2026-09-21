package ru.pulsarmn.messenger.auth.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.pulsarmn.messenger.auth.exception.BadCredentialsException;
import ru.pulsarmn.messenger.auth.exception.RegistrationException;
import ru.pulsarmn.messenger.auth.exception.ServiceUnavailableException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail handlerBadCredentials(BadCredentialsException ex) {
        log.warn("Invalid data", ex);
        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationException.class)
    ProblemDetail handleRegistration(RegistrationException ex) {
        log.warn("Error during registration", ex);
        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    ProblemDetail handleServiceUnavailable(ServiceUnavailableException ex) {
        log.error("Some service is unavailable", ex);
        return ProblemDetail.forStatus(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Invalid method argument(-s)", ex);
        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception ex) {
        log.error("An unexpected error occurred", ex);
        return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
