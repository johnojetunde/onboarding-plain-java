package com.telesoftas.onboarding.domain.librarian.validator;

import com.telesoftas.onboarding.app.service.ValidatorStub;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.model.LibrarianFixture;
import com.telesoftas.onboarding.domain.validator.ModelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianModelValidatorTest {

    private ModelValidator<Librarian> validator;

    private Librarian librarian;

    @BeforeEach
    void setUp() {
        validator = new LibrarianModelValidator(
            ValidatorStub.validatorWithUniqueEmailContext(false, false)
        );

        librarian = LibrarianFixture.validLibrarian();
    }


    @Test
    void should_throw_an_exception_when_firstname_is_blank() {
        try {
            librarian.setFirstname("");

            validator.validate(librarian, RegistrationGroup.class);

            fail();
        } catch (ViolationException e) {
            assertFalse(e.getViolations().isEmpty());
            assertTrue(e.getViolations().get("firstname").contains("Firstname is required"));
        }

    }


    @Test
    void should_throw_an_exception_when_password_is_invalid() {
        try {
            librarian.setPassword("1234");

            validator.validate(librarian, RegistrationGroup.class);


            fail();
        } catch (ViolationException e) {
            assertTrue(e.getViolations().get("password").contains("Password must be at least 8 characters"));
        }

    }


    @Test
    void should_throw_an_exception_when_email_is_invalid() {
        try {

            librarian.setEmail("gfhfhfhfhf");

            validator.validate(librarian, RegistrationGroup.class);

            fail();
        } catch (ViolationException e) {
            assertTrue(e.getViolations().get("email").contains("Email is invalid"));
        }

    }

    @Test
    void should_throw_an_exception_when_email_is_blank() {
        try {

            librarian.setEmail("");

            validator.validate(librarian, ProfileGroup.class);

            fail();
        } catch (ViolationException e) {
            assertTrue(e.getViolations().get("email").contains("Email is required"));
        }

    }


    @Test
    void should_throw_an_exception_if_librarian_email_is_existing() {
        try {
            validator = new LibrarianModelValidator(
                ValidatorStub.validatorWithUniqueEmailContext(true, false)
            );

            validator.validate(librarian, RegistrationGroup.class);

            fail();
        } catch (ViolationException e) {
            assertEquals(1, e.getViolations().size());
            assertTrue(e.getViolations().get("email").contains("Librarian with this email exists"));
        }
    }

    @Test
    void should_throw_an_exception_if_librarian_email_existing_validation_fails() {
        try {
            validator = new LibrarianModelValidator(
                ValidatorStub.validatorWithUniqueEmailContext(true, true)
            );

            validator.validate(librarian, RegistrationGroup.class);

            fail();
        } catch (ViolationException e) {
            assertEquals(1, e.getViolations().size());
            assertTrue(e.getViolations().get("email").contains("Librarian with this email exists"));
        }
    }
}
