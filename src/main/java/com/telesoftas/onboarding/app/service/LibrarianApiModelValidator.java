package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.model.LibrarianApiRequestModel;
import com.telesoftas.onboarding.domain.validator.ModelValidator;

import javax.validation.Validator;

public class LibrarianApiModelValidator extends ModelValidator<LibrarianApiRequestModel> {
    public LibrarianApiModelValidator(Validator validator) {
        super(validator);
    }
}
