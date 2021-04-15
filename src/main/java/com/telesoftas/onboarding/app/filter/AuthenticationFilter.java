package com.telesoftas.onboarding.app.filter;

import com.telesoftas.onboarding.app.exception.EmptyTokenException;
import com.telesoftas.onboarding.app.service.ResponseBuilder;
import com.telesoftas.onboarding.app.service.TokenExtractor;
import com.telesoftas.onboarding.domain.librarian.exception.ExpireTokenException;
import com.telesoftas.onboarding.domain.librarian.exception.MalformedTokenException;
import com.telesoftas.onboarding.domain.librarian.token.TokenVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final TokenVerification tokenVerification;

    private final ResponseBuilder responseBuilder;

    private final TokenExtractor tokenExtractor;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            tokenVerification.verify(tokenExtractor.extract(((HttpServletRequest) servletRequest)));

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (EmptyTokenException e) {
            responseBuilder.buildResponseWithError((HttpServletResponse) servletResponse, "Request does not contain Token", HttpServletResponse.SC_BAD_REQUEST);
        } catch (ExpireTokenException e) {
            responseBuilder.buildResponseWithError((HttpServletResponse) servletResponse, "Token Expired", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (MalformedTokenException e) {
            responseBuilder.buildResponseWithError((HttpServletResponse) servletResponse, "Invalid Token", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Invalid Token", e);
            responseBuilder.buildResponseWithError((HttpServletResponse) servletResponse, "Invalid Token", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {
    }
}
