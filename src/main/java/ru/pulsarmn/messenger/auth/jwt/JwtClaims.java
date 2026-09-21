package ru.pulsarmn.messenger.auth.jwt;

import com.nimbusds.jwt.JWTClaimNames;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtClaims {

    private final Map<String, Object> claims;

    private JwtClaims(Map<String, Object> claims) {
        this.claims = claims;
    }

    public Object getClaim(String name) {
        return claims.get(name);
    }

    public String getSubject() {
        Object value = claims.get(JWTClaimNames.SUBJECT);
        if (value instanceof String subject) {
            return subject;
        }
        return null;
    }

    public Instant getExpirationTime() {
        return getInstantClaim(JWTClaimNames.EXPIRATION_TIME);
    }

    public Instant getIssueTime() {
        return getInstantClaim(JWTClaimNames.ISSUED_AT);
    }

    private Instant getInstantClaim(String name) {
        try {
            return getDateClaim(name).toInstant();
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private Date getDateClaim(String name) {
        Object value = claims.get(name);
        if (value instanceof Date date) {
            return date;
        } else {
            throw new IllegalStateException("The %s is not a Date".formatted(value));
        }
    }

    public Map<String, Object> getClaims() {
        return Collections.unmodifiableMap(claims);
    }

    public static class Builder {
        private final Map<String, Object> claims = new HashMap<>();

        public Builder subject(String sub) {
            claims.put(JWTClaimNames.SUBJECT, sub);
            return this;
        }

        public Builder expirationTime(Instant expirationTime) {
            claims.put(JWTClaimNames.EXPIRATION_TIME, Date.from(expirationTime));
            return this;
        }

        public Builder issueTime(Instant issueTime) {
            claims.put(JWTClaimNames.ISSUED_AT, Date.from(issueTime));
            return this;
        }

        public Builder claim(String name, Object value) {
            claims.put(name, value);
            return this;
        }

        public JwtClaims build() {
            return new JwtClaims(claims);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
