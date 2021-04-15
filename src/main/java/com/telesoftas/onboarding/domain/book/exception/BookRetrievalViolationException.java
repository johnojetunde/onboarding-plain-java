package com.telesoftas.onboarding.domain.book.exception;

import com.telesoftas.onboarding.domain.exception.ViolationException;

import java.util.List;
import java.util.Map;

public class BookRetrievalViolationException extends ViolationException {
    public BookRetrievalViolationException(String message, Map<String, List<String>> violations) {
        super(message, violations);
    }
}
