package com.telesoftas.onboarding.domain.librarian.exception;

public class LibrarianServiceException extends Exception {

    public LibrarianServiceException(String message) {
        super(message);
    }
    public LibrarianServiceException(String message, Throwable e) {
        super(message, e);
    }
}
