package com.telesoftas.onboarding.domain.librarian.token;

import com.telesoftas.onboarding.domain.librarian.exception.ExpireTokenException;
import com.telesoftas.onboarding.domain.librarian.exception.MalformedTokenException;
import com.telesoftas.onboarding.domain.librarian.exception.TokenVerificationException;

public interface TokenVerification {
    void verify(String token) throws ExpireTokenException, MalformedTokenException, TokenVerificationException;
}
