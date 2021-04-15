package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.exception.AppViolationException;
import com.telesoftas.onboarding.app.model.LibrarianApiRequestModel;
import com.telesoftas.onboarding.app.model.LibrarianApiResponseModel;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.validator.ModelValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RequiredArgsConstructor
public class LibrarianApiModelMapper {

    private final ModelValidator<LibrarianApiRequestModel> validator;


    LibrarianApiResponseModel toResponseModel(@NonNull Librarian model) {
        return LibrarianApiResponseModel.builder()
            .firstname(model.getFirstname())
            .lastname(model.getLastname())
            .email(model.getEmail())
            .build();
    }

    public LibrarianApiRequestModel mapToRequestModel(@NonNull HttpServletRequest request) throws IOException, AppViolationException {
        try (BufferedReader reader = request.getReader();
             final JsonReader jsonReader = Json.createReader(reader)) {
            JsonObject jsonObject = jsonReader.readObject();

            LibrarianApiRequestModel model = mapToRequestModel(jsonObject);
            validator.validate(model);

            return model;
        } catch (ViolationException e) {
            throw new AppViolationException(e.getMessage(), e.getViolations());
        }
    }

    LibrarianApiRequestModel mapToRequestModel(JsonObject jsonObject) {
        return LibrarianApiRequestModel.builder()
            .firstname(jsonObject.getString("firstname", null))
            .lastname(jsonObject.getString("lastname", null))
            .email(jsonObject.getString("email", null))
            .password(jsonObject.getString("password", null))
            .build();
    }


}
