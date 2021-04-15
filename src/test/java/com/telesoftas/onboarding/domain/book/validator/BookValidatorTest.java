package com.telesoftas.onboarding.domain.book.validator;

import com.telesoftas.onboarding.app.service.ValidatorStub;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookValidatorTest {

    private BookValidator validator;

    @BeforeEach
    void setUp() {
        validator = new BookValidator(ValidatorStub.validator());
    }

    @Test
    void should_throw_an_exception_when_name_is_blank() {
        try {
            validator.byTitle("");
        } catch (ViolationException e) {
            assertFalse(e.getViolations().isEmpty());
            assertTrue(e.getViolations().get("title").contains("Book title is required"));
        }
    }
}
