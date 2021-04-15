package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.model.LibrarianApiRequestModel;
import com.telesoftas.onboarding.app.model.LibrarianApiResponseModel;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.model.LibrarianFixture;
import com.telesoftas.onboarding.domain.validator.ModelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.validation.Validator;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.*;

class LibrarianApiModelMapperTest {

    private LibrarianApiModelMapper librarianApiModelMapper;

    @BeforeEach
    void setUp() {

        Validator validator = ValidatorStub.validatorWithUniqueEmailContext(false, false);

        ModelValidator<LibrarianApiRequestModel> modelValidator = new LibrarianApiModelValidator(validator);
        librarianApiModelMapper = new LibrarianApiModelMapper(modelValidator);
    }

    @Test
    void entity_cannot_be_null() {
        assertThrows(NullPointerException.class,
            () -> librarianApiModelMapper.toResponseModel(null));
    }


    @Test
    void should_map_librarian_model_to_response_model_successfully() {
        Librarian librarian = LibrarianFixture.validLibrarian();

        LibrarianApiResponseModel responseModel = librarianApiModelMapper.toResponseModel(librarian);

        assertEquals(librarian.getFirstname(), responseModel.getFirstname());
        assertEquals(librarian.getLastname(), responseModel.getLastname());
        assertEquals(librarian.getEmail(), responseModel.getEmail());
    }


    @Test
    void should_map_json_object_to_model_successfully() {
        JsonBuilderFactory factory = Json.createBuilderFactory(singletonMap(JsonGenerator.PRETTY_PRINTING, true));
        JsonObject jsonObject = factory.createObjectBuilder()
            .add("firstname", "firstname")
            .add("lastname", "lastname")
            .add("email", "email")
            .add("password", "password").build();

        LibrarianApiRequestModel requestModel = librarianApiModelMapper.mapToRequestModel(jsonObject);

        assertEquals("firstname", requestModel.getFirstname());
        assertEquals("lastname", requestModel.getLastname());
        assertEquals("email", requestModel.getEmail());
        assertEquals("password", requestModel.getPassword());
    }

    @Test
    void should_set_model_attr_to_null_value_for_absent_json_properties() {
        JsonBuilderFactory factory = Json.createBuilderFactory(singletonMap(JsonGenerator.PRETTY_PRINTING, true));
        JsonObject jsonObject = factory.createObjectBuilder()
            .add("firstname", "firstname")
            .add("lastname", "lastname")
            .add("password", "password").build();

        LibrarianApiRequestModel requestModel = librarianApiModelMapper.mapToRequestModel(jsonObject);

        assertNull(requestModel.getEmail());
    }


}
