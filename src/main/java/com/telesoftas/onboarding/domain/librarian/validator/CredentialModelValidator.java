package com.telesoftas.onboarding.domain.librarian.validator;

import com.telesoftas.onboarding.domain.librarian.model.Credential;
import com.telesoftas.onboarding.domain.validator.ModelValidator;

import javax.validation.Validator;

public class CredentialModelValidator extends ModelValidator<Credential> {
    public CredentialModelValidator(Validator validator) {
        super(validator);
    }
}

