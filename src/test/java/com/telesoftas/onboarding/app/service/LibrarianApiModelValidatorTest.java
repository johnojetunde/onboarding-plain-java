package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.fixtures.LibrarianApiRequestModelFixture;
import com.telesoftas.onboarding.app.model.LibrarianApiRequestModel;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class LibrarianApiModelValidatorTest {

    private LibrarianApiModelValidator librarianModelValidator;

    private LibrarianApiRequestModel model;

    @BeforeEach
    void setUp() {
        Validator validator = ValidatorStub.validatorWithUniqueEmailContext(false, false);

        model = LibrarianApiRequestModelFixture.valid();
        librarianModelValidator = new LibrarianApiModelValidator(validator);
    }

    @Test
    void validation_should_throw_violation_exception_with_exception_messages_matching_the_violations() {
        model = LibrarianApiRequestModelFixture.valid();
        model.setFirstname(null);
        model.setEmail(null);

        try {
            librarianModelValidator.validate(model);
            fail();
        } catch (ViolationException e) {

            assertTrue(e.getViolations().get("firstname").contains("Firstname is a required field"));
            assertTrue(e.getViolations().get("email").contains("Email is a required field"));
        }
    }


    @Test
    void validation_should_run_without_exception() throws ViolationException {
        model = LibrarianApiRequestModelFixture.valid();

        librarianModelValidator.validate(model);
    }


}
