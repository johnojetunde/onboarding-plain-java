package com.telesoftas.onboarding.domain.librarian.password;

import com.telesoftas.onboarding.domain.librarian.exception.PasswordMatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BcryptPasswordTest {

    private BcryptPassword bcryptPasswordHasher;

    @BeforeEach
    public void setUp() {
        bcryptPasswordHasher = new BcryptPassword();
    }

    @Test
    void hashPassword() {
        String password = "OnboardingProject";
        String hashedPassword = bcryptPasswordHasher.hash(password);

        assertNotNull(hashedPassword);
        assertEquals(60, hashedPassword.length());
    }

    @Test
    void plain_password_should_match_hashed_password() throws PasswordMatchException {
        String password = "OnboardingProject";
        String hashedPassword = "$2a$10$A.0cPjEOQ26b2hR6lpoHDeStmoK9IGiiMKRxdEMHS4CkwSw3norJu";

        bcryptPasswordHasher.match(password, hashedPassword);
    }

    @Test
    void should_throw_an_exception_if_password_does_not_match() {
        String password = "Onboarding";
        String hashedPassword = "$2a$10$A.0cPjEOQ26b2hR6lpoHDeStmoK9IGiiMKRxdEMHS4CkwSw3norJu";

        assertThrows(PasswordMatchException.class, () -> bcryptPasswordHasher.match(password, hashedPassword));

    }
}
