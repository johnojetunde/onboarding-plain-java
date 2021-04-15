package com.telesoftas.onboarding.domain.librarian.stubs;

import com.telesoftas.onboarding.domain.librarian.exception.PasswordMatchException;
import com.telesoftas.onboarding.domain.librarian.password.PasswordMatcher;

public class PasswordMatcherStub {

    public static PasswordMatcher newPasswordMatcher(boolean isMatched) {
        return (plainPassword, hashedPassword) -> {
            if (!isMatched) {
                throw new PasswordMatchException("Password does not match");
            }
        };
    }
}
