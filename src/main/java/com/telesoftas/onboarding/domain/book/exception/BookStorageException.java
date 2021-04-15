package com.telesoftas.onboarding.domain.book.exception;

public class BookStorageException extends Exception {
    public BookStorageException(String message) {
        super(message);
    }

    public BookStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
