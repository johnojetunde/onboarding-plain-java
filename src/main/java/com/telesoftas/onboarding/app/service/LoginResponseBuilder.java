package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.domain.librarian.model.AccessToken;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import lombok.NonNull;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginResponseBuilder extends ResponseBuilder {

    public LoginResponseBuilder(JsonBuilderFactory jsonBuilderFactory) {
        super(jsonBuilderFactory);
    }

    public void buildSuccessfulResponse(@NonNull HttpServletResponse resp, @NonNull AccessToken accessToken) throws IOException {
        Librarian librarian = accessToken.getLibrarian();

        JsonObject librarianObject = jsonBuilderFactory.createObjectBuilder().add("firstname", librarian.getFirstname())
            .add("lastname", librarian.getLastname())
            .add("email", librarian.getEmail())
            .add("time_created", librarian.getTimeCreated().toString())
            .add("time_modified", (librarian.getTimeModified() == null) ? "" : librarian.getTimeModified().toString())
            .build();

        JsonObject responseObject = jsonBuilderFactory.createObjectBuilder()
            .add("librarian", librarianObject)
            .add("token", accessToken.getToken())
            .build();

        resp.setContentType("application/json");
        writeResponse(resp, responseObject, HttpServletResponse.SC_OK);
    }
}
