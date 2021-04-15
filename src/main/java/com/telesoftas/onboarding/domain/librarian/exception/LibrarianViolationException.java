package com.telesoftas.onboarding.domain.librarian.exception;

import com.telesoftas.onboarding.domain.exception.ViolationException;

import java.util.List;
import java.util.Map;

public class LibrarianViolationException extends ViolationException {
    public LibrarianViolationException(String message, Map<String, List<String>> violations) {
        super(message, violations);
    }
}
