package ru.pulsarmn.messenger.auth.exception;


public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String message) {
        super(message);
    }
}
