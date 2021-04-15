package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.domain.librarian.exception.LibrarianUniqueEmailCheckException;

public interface LibrarianEmailExistence {
    boolean isEmailExisting(String email) throws LibrarianUniqueEmailCheckException;
}
