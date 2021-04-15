package com.telesoftas.onboarding.domain.librarian.model;

import com.telesoftas.onboarding.app.service.ValidatorStub;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import com.telesoftas.onboarding.domain.librarian.validator.CredentialModelValidator;
import com.telesoftas.onboarding.domain.validator.ModelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CredentialModelTest {

    private ModelValidator<Credential> validator;

    private Credential credential;

    @BeforeEach
    void setUp() {
        validator = new CredentialModelValidator(ValidatorStub.validator());

        credential = CredentialFixture.valid();
    }


    @Test
    void should_throw_an_exception_when_required_fields_are_absent() {
        try {

            credential.setEmail("");
            credential.setPassword("");

            validator.validate(credential);

            fail();
        } catch (ViolationException e) {
            assertTrue(e.getViolations().get("email").contains("Email is required"));
            assertTrue(e.getViolations().get("password").contains("Password is required"));
        }
    }

    @Test
    void should_throw_an_exception_when_email_is_invalid() {
        try {

            credential.setEmail("gfhfhfhfhf");

            validator.validate(credential);

            fail();
        } catch (ViolationException e) {
            assertTrue(e.getViolations().get("email").contains("Email is invalid"));
        }

    }


}
