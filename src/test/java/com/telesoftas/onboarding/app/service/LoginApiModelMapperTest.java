package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.model.LoginRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.validation.Validator;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LoginApiModelMapperTest {

    private LoginApiModelMapper mapper;

    @BeforeEach
    void setUp() {

        Validator validator = ValidatorStub.validatorWithUniqueEmailContext(false, false);

        LoginApiModelValidator modelValidator = new LoginApiModelValidator(validator);
        mapper = new LoginApiModelMapper(modelValidator);
    }


    @Test
    void should_map_json_object_to_model_successfully() {
        JsonBuilderFactory factory = Json.createBuilderFactory(singletonMap(JsonGenerator.PRETTY_PRINTING, true));
        JsonObject jsonObject = factory.createObjectBuilder()
            .add("email", "email")
            .add("password", "password").build();

        LoginRequestModel requestModel = mapper.mapToRequestModel(jsonObject);

        assertEquals("email", requestModel.getEmail());
        assertEquals("password", requestModel.getPassword());
    }

    @Test
    void should_set_model_attr_to_null_value_for_absent_json_properties() {
        JsonBuilderFactory factory = Json.createBuilderFactory(singletonMap(JsonGenerator.PRETTY_PRINTING, true));
        JsonObject jsonObject = factory.createObjectBuilder()
            .add("password", "password").build();

        LoginRequestModel requestModel = mapper.mapToRequestModel(jsonObject);
        assertNull(requestModel.getEmail());
    }

}
