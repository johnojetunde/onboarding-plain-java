package com.telesoftas.onboarding.domain.librarian.exception;

public class LibrarianSaveException extends Exception {

    public LibrarianSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibrarianSaveException(String message) {
        super(message);
    }
}
