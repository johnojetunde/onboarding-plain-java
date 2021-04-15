package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.exception.AppViolationException;
import com.telesoftas.onboarding.app.model.LoginRequestModel;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RequiredArgsConstructor
public class LoginApiModelMapper {

    private final LoginApiModelValidator validator;

    public LoginRequestModel mapToRequestModel(@NonNull HttpServletRequest request) throws IOException, AppViolationException {
        try (BufferedReader reader = request.getReader();
             final JsonReader jsonReader = Json.createReader(reader)) {
            JsonObject jsonObject = jsonReader.readObject();

            LoginRequestModel model = mapToRequestModel(jsonObject);

            validator.validate(model);

            return model;
        } catch (ViolationException e) {
            throw new AppViolationException(e.getMessage(), e.getViolations());
        }
    }

    LoginRequestModel mapToRequestModel(@NonNull JsonObject jsonObject) {
        return LoginRequestModel.builder()
            .email(jsonObject.getString("email", null))
            .password(jsonObject.getString("password", null))
            .build();

    }

}
