package com.telesoftas.onboarding.app.service;

import lombok.AllArgsConstructor;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ResponseBuilder {

    final JsonBuilderFactory jsonBuilderFactory;

    public void buildResponseWithError(HttpServletResponse resp, String errorMessage) throws IOException {

        buildResponseWithError(resp, errorMessage, HttpServletResponse.SC_BAD_REQUEST);
    }


    public void buildResponseWithError(HttpServletResponse resp, String errorMessage, int responseCode) throws IOException {
        JsonObject responseObject = jsonBuilderFactory.createObjectBuilder()
            .add("error", errorMessage).build();

        resp.setContentType("application/problem+json");
        writeResponse(resp, responseObject, responseCode);
    }


    public void buildResponseWithViolationErrors(HttpServletResponse resp, Map<String, List<String>> errorMap) throws IOException {
        buildResponseWithViolationErrors(resp, errorMap, HttpServletResponse.SC_BAD_REQUEST);
    }

    public void buildResponseWithViolationErrors(HttpServletResponse resp,
                                                 Map<String, List<String>> violations,
                                                 int responseCode) throws IOException {
        JsonObjectBuilder objectBuilder = jsonBuilderFactory.createObjectBuilder();

        violations.forEach((k, v) -> objectBuilder.add(k, jsonBuilderFactory.createArrayBuilder(v).build()));

        JsonObject responseObject = jsonBuilderFactory.createObjectBuilder()
            .add("title", "Your request parameters didn't validate")
            .add("invalid_params", objectBuilder.build()).build();


        resp.setContentType("application/problem+json");
        writeResponse(resp, responseObject, responseCode);
    }

    public void buildSuccessfulResponse(HttpServletResponse resp, String responseMessage) throws IOException {
        JsonObject responseObject = jsonBuilderFactory.createObjectBuilder()
            .add("successMessage", responseMessage).build();

        resp.setContentType("application/json");
        writeResponse(resp, responseObject, HttpServletResponse.SC_OK);
    }

    void writeResponse(HttpServletResponse resp, JsonObject responseObject, int statusCode) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            resp.setStatus(statusCode);
            out.print(responseObject);
            out.flush();
        }
    }
}
