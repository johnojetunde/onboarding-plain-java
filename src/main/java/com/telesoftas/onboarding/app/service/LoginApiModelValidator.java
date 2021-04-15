package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.model.LoginRequestModel;
import com.telesoftas.onboarding.domain.validator.ModelValidator;

import javax.validation.Validator;

public class LoginApiModelValidator extends ModelValidator<LoginRequestModel> {

    public LoginApiModelValidator(Validator validator) {
        super(validator);
    }
}
