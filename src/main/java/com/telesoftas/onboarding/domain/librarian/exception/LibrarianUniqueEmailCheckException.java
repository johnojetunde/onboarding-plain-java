package com.telesoftas.onboarding.domain.librarian.exception;

public class LibrarianUniqueEmailCheckException extends  Exception {
    public LibrarianUniqueEmailCheckException(String message, Throwable e) {
        super(message,e);
    }

    public LibrarianUniqueEmailCheckException(String message) {
        super(message);
    }
}
