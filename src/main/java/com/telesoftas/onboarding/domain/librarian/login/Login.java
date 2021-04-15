package com.telesoftas.onboarding.domain.librarian.login;

import com.telesoftas.onboarding.domain.exception.ViolationException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.model.AccessToken;
import com.telesoftas.onboarding.domain.librarian.model.Credential;

public interface Login {
    AccessToken login(Credential credential) throws ViolationException, LibrarianServiceException;
}
