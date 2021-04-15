package com.telesoftas.onboarding.domain.librarian.exception;

public class TokenGenerationException extends Exception {
    public TokenGenerationException(String message, Throwable e) {
        super(message, e);
    }

    public TokenGenerationException(String message) {
        super(message);
    }
}
