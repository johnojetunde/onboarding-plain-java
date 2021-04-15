package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.factory.ValidatorFactory;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValidatorFactoryTest {

    @Test
    void getValidator() {
        Validator validator = ValidatorFactory.getValidator();

        assertNotNull(validator);
    }
}
