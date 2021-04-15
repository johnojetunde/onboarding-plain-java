package com.telesoftas.onboarding.app.servlet;

import com.telesoftas.onboarding.app.model.Version;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatusServlet extends HttpServlet {

    private Version version;

    public StatusServlet(Version version) {
        this.version = version;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        JsonObject response = Json.createObjectBuilder()
            .add("status", "ok")
            .add("version", version.getVersion())
            .build();

        Json.createWriter(resp.getOutputStream())
            .writeObject(response);
    }
}
