package com.telesoftas.onboarding.domain.librarian.login;

import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;

import java.util.Optional;

public interface LibrarianFindByEmail {
    Optional<Librarian> findByEmail(String email) throws LibrarianServiceException;
}
