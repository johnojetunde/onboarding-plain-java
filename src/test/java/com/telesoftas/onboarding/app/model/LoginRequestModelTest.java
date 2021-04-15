package com.telesoftas.onboarding.app.model;

import com.telesoftas.onboarding.app.fixtures.LoginRequestModelFixture;
import com.telesoftas.onboarding.app.service.ValidatorStub;
import com.telesoftas.onboarding.domain.librarian.model.Credential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginRequestModelTest {

    private Validator validator;

    private LoginRequestModel requestModel;

    @BeforeEach
    void setUp() {
        validator = ValidatorStub.validator();

        requestModel = LoginRequestModelFixture.valid();
    }


    @Test
    void invalid_email() {
        requestModel.setEmail(null);

        Set<ConstraintViolation<LoginRequestModel>> result = validator.validate(requestModel);

        assertEquals(1, result.size());

        result.forEach(err -> {
            assertEquals("email", err.getPropertyPath().toString());
            assertEquals("Email is a required field", err.getMessage());
        });
    }


    @Test
    void invalid_password() {
        requestModel.setPassword(null);

        Set<ConstraintViolation<LoginRequestModel>> result = validator.validate(requestModel);

        assertEquals(1, result.size());

        result.forEach(err -> {
            assertEquals("password", err.getPropertyPath().toString());
            assertEquals("Password is required", err.getMessage());
        });

    }


    @Test
    void valid_entity() {
        Set<ConstraintViolation<LoginRequestModel>> result = validator.validate(requestModel);
        assertEquals(0, result.size());

    }

    @Test
    void maps_successfully_to_librarian_model() {
        LoginRequestModel requestModel = LoginRequestModelFixture.valid();
        Credential model = requestModel.getCredential();


        assertEquals(requestModel.getEmail(), model.getEmail());
        assertEquals(requestModel.getPassword(), model.getPassword());
    }
}
