package com.telesoftas.onboarding.app.model;

import com.telesoftas.onboarding.app.fixtures.LibrarianApiRequestModelFixture;
import com.telesoftas.onboarding.app.service.ValidatorStub;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LibrarianApiRequestModelTest {

    private Validator validator;

    private LibrarianApiRequestModel librarianApiRequestModel;

    @BeforeEach
    void setUp() {
        validator = ValidatorStub.validator();

        librarianApiRequestModel = LibrarianApiRequestModelFixture.valid();
    }


    @Test
    void invalid_Firstname() {
        librarianApiRequestModel.setFirstname(null);

        Set<ConstraintViolation<LibrarianApiRequestModel>> result = validator.validate(librarianApiRequestModel);

        assertEquals(1, result.size());

        result.forEach(err -> {
            assertEquals("firstname", err.getPropertyPath().toString());
            assertEquals("Firstname is a required field", err.getMessage());
        });
    }


    @Test
    void invalid_lastname() {
        librarianApiRequestModel.setLastname(null);

        Set<ConstraintViolation<LibrarianApiRequestModel>> result = validator.validate(librarianApiRequestModel);

        assertEquals(1, result.size());

        result.forEach(err -> {
            assertEquals("lastname", err.getPropertyPath().toString());
            assertEquals("Lastname is a required field", err.getMessage());
        });

    }


    @Test
    void valid_entity() {
        Set<ConstraintViolation<LibrarianApiRequestModel>> result = validator.validate(librarianApiRequestModel);
        assertEquals(0, result.size());

    }

    @Test
    void maps_successfully_to_librarian_model() {
        LibrarianApiRequestModel requestModel = LibrarianApiRequestModelFixture.valid();
        Librarian model = requestModel.getLibrarian();

        assertEquals(requestModel.getFirstname(), model.getFirstname());
        assertEquals(requestModel.getLastname(), model.getLastname());
        assertEquals(requestModel.getEmail(), model.getEmail());
        assertEquals(requestModel.getPassword(), model.getPassword());
    }

}
