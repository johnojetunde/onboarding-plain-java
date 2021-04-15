package com.telesoftas.onboarding.domain.librarian.stubs;

import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.login.LibrarianFindByEmail;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;

import java.util.Optional;

public class LibrarianFindByEmailStub {

    public static LibrarianFindByEmail newFindByEmail(Librarian librarian, boolean throwException) {
        return librarianStoage -> {
            if (librarian != null) {
                return Optional.of(librarian);
            }

            if (throwException) {
                throw new LibrarianServiceException("Unable to retrieve librarian by email");
            }

            return Optional.empty();
        };
    }
}
