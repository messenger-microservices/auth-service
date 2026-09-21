package ru.pulsarmn.messenger.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;


public record UserCreateRequest(

        @NotNull
        UUID id,

        @NotBlank
        @Size(min = 2, max = 32)
        String username,

        String phoneNumber,
        String displayName,
        LocalDate birthdate) {

    public static class Builder {
        private UUID id;
        private String username;
        private String phoneNumber;
        private String displayName;
        private LocalDate birthdate;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder birthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public UserCreateRequest build() {
            return new UserCreateRequest(id, username, phoneNumber, displayName, birthdate);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
