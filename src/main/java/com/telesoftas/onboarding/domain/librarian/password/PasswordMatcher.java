package com.telesoftas.onboarding.domain.librarian.password;

import com.telesoftas.onboarding.domain.librarian.exception.PasswordMatchException;

public interface PasswordMatcher {
    void match(String plainPassword, String hashedPassword) throws PasswordMatchException;
}
