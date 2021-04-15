package com.telesoftas.onboarding.domain.librarian.validator;

import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.validator.ModelValidator;

import javax.validation.Validator;

public class LibrarianModelValidator extends ModelValidator<Librarian> {
    public LibrarianModelValidator(Validator validator) {
        super(validator);
    }

}
