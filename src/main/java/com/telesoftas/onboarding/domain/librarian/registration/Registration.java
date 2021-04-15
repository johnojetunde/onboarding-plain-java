package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianViolationException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;

public interface Registration {
    void register(Librarian librarian) throws LibrarianServiceException, LibrarianViolationException;
}
