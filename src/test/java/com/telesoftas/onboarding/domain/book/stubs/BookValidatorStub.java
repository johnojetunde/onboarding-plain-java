package com.telesoftas.onboarding.domain.book.stubs;

import com.telesoftas.onboarding.domain.book.validator.BookValidator;
import com.telesoftas.onboarding.domain.exception.ViolationException;

import javax.validation.Validator;
import java.util.HashMap;

public class BookValidatorStub extends BookValidator {

    private boolean throwException;

    public BookValidatorStub(Validator validator, boolean throwException) {
        super(validator);
        this.throwException = throwException;
    }

    @Override
    public void byTitle(String title) throws ViolationException {
        if (throwException) {
            HashMap errorMap = new HashMap() {{
                put("title", "Book title is required");
            }};

            throw new ViolationException("Book title is required", errorMap);
        }
        super.byTitle(title);
    }
}
