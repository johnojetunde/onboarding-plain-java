package com.telesoftas.onboarding.domain.librarian.stubs;

import com.telesoftas.onboarding.domain.librarian.exception.LibrarianUniqueEmailCheckException;
import com.telesoftas.onboarding.domain.librarian.registration.LibrarianEmailExistence;

public class LibrarianEmailExistenceStub {

    public static LibrarianEmailExistence newLibrarianEmailExistence(boolean isEmailExisting, boolean throwUniqueEmailException) {
        return librarianEmailExistence -> {
            if (throwUniqueEmailException) {
                throw new LibrarianUniqueEmailCheckException("Error checking if email exists");
            }
            return isEmailExisting;
        };
    }
}
