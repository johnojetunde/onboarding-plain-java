package com.telesoftas.onboarding.domain.book.exception;

public class BookRetrievalException extends Exception {

    public BookRetrievalException(String message) {
        super(message);
    }

    public BookRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookRetrievalException(Throwable cause) {
        super(cause);
    }
}
