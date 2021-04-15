package com.telesoftas.onboarding.domain.book.validator;

import com.telesoftas.onboarding.domain.book.model.Book;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import com.telesoftas.onboarding.domain.validator.ModelValidator;

import javax.validation.Validator;

public class BookValidator extends ModelValidator<Book> {

    public BookValidator(Validator validator) {
        super(validator);
    }

    public void byTitle(String title) throws ViolationException {
        this.validateValue(Book.class, "title", title);
    }
}
