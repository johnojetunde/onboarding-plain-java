package com.telesoftas.onboarding.app.servlet;

import com.telesoftas.onboarding.app.exception.AppViolationException;
import com.telesoftas.onboarding.app.service.LibrarianApiModelMapper;
import com.telesoftas.onboarding.app.service.ResponseBuilder;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianViolationException;
import com.telesoftas.onboarding.domain.librarian.registration.Registration;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

@Log
@AllArgsConstructor
public class LibrarianServlet extends HttpServlet {

    private final LibrarianApiModelMapper librarianApiModelMapper;

    private final ResponseBuilder responseBuilder;

    private final Registration registration;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            registration.register(librarianApiModelMapper.mapToRequestModel(req).getLibrarian());

            responseBuilder.buildSuccessfulResponse(resp, "Registration successful");

        } catch (LibrarianServiceException e) {
            responseBuilder.buildResponseWithError(resp, e.getMessage());
        } catch (AppViolationException | LibrarianViolationException e) {
            responseBuilder.buildResponseWithViolationErrors(resp, e.getViolations());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error completing librarian registration", e);
            responseBuilder.buildResponseWithError(resp, "Unable to complete registration request");
        }
    }


}
