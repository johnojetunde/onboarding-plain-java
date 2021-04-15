package com.telesoftas.onboarding.app.factory;

import com.telesoftas.onboarding.domain.librarian.registration.LibrarianEmailExistence;

public interface LibrarianEmailExistingAwareValidator {
    void setEmailExisting(LibrarianEmailExistence emailExisting);
}
