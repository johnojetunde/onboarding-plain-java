package com.telesoftas.onboarding.app.servlet;

import com.telesoftas.onboarding.app.service.LoginApiModelMapper;
import com.telesoftas.onboarding.app.service.LoginResponseBuilder;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.login.Login;
import com.telesoftas.onboarding.domain.librarian.model.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public class LoginServlet extends HttpServlet {

    private final LoginApiModelMapper mapper;

    private final LoginResponseBuilder responseBuilder;

    private final Login authentication;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AccessToken accessToken = authentication.login(mapper.mapToRequestModel(req).getCredential());


            responseBuilder.buildSuccessfulResponse(resp, accessToken);
        } catch (ViolationException e) {
            responseBuilder.buildResponseWithViolationErrors(resp, e.getViolations());
        } catch (LibrarianServiceException e) {
            responseBuilder.buildResponseWithError(resp, e.getMessage());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error completing librarian login", e);
            responseBuilder.buildResponseWithError(resp, "Unable to complete librarian login request");
        }

    }
}
