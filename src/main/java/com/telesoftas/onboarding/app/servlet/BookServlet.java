package com.telesoftas.onboarding.app.servlet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        JsonObject response = Json.createObjectBuilder()
            .add("status", "ok")
            .add("module", "Book Search!!!!!")
            .build();

        Json.createWriter(resp.getOutputStream())
            .writeObject(response);

    }
}
