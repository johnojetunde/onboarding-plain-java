package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.model.LibrarianFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PasswordHashRegistrationProcessorTest {

    private PasswordHashRegistrationProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new PasswordHashRegistrationProcessor(plainPassword -> "$2a$10$A.0cPjEOQ26b2hR6lpoHDeStmoK9IGiiMKRxdEMHS4CkwSw3norJu");
    }

    @Test
    void should_furnish_librarian_model_with_the_hashed_password_from_passwordhasher() {
        Librarian librarian = LibrarianFixture.validLibrarian();
        librarian.setPassword("telesoftas");

        processor.process(librarian);

        assertNotEquals("telesoftas", librarian.getPassword());
        assertEquals("$2a$10$A.0cPjEOQ26b2hR6lpoHDeStmoK9IGiiMKRxdEMHS4CkwSw3norJu", librarian.getPassword());
    }
}
