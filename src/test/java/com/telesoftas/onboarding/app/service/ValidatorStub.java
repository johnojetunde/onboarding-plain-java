package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.factory.ValidatorFactory;
import com.telesoftas.onboarding.domain.librarian.stubs.LibrarianEmailExistenceStub;

import javax.validation.Validator;

public class ValidatorStub {


    public static Validator validator() {
        return ValidatorFactory
            .getValidator();

    }

    public static Validator validatorWithUniqueEmailContext(boolean isEmailExisting, boolean throwUniqueEmailException) {
        return ValidatorFactory
            .getValidatorWithUniqueEmailContext
                (LibrarianEmailExistenceStub.newLibrarianEmailExistence(isEmailExisting, throwUniqueEmailException));

    }
}
