package com.telesoftas.onboarding.domain.librarian.token;

import com.telesoftas.onboarding.domain.librarian.exception.TokenGenerationException;
import com.telesoftas.onboarding.domain.librarian.model.AccessToken;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;

public interface TokenGenerator {
    AccessToken generate(Librarian librarian) throws TokenGenerationException;
}
