package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.exception.EmptyTokenException;

import javax.servlet.http.HttpServletRequest;

public class BearerTokenExtractor implements TokenExtractor {

    @Override
    public String extract(HttpServletRequest request) throws EmptyTokenException {

        String authHeaderVal = request.getHeader("Authorization");

        return fromHeader(authHeaderVal);
    }


    String fromHeader(String authHeaderVal) throws EmptyTokenException {
        try {
            if (!isNullOrEmpty(authHeaderVal) && authHeaderVal.startsWith("Bearer")) {
                String token = authHeaderVal.split(" ")[1];
                if (!isNullOrEmpty(token)) return token;
            }
        } catch (ArrayIndexOutOfBoundsException Ignored) {
            //should not do anything. The EmptyAccessTokenException thrown later in this method will handle this
        }

        throw new EmptyTokenException("Request does not contain token");
    }


    private boolean isNullOrEmpty(String s) {
        return (s == null || s.isEmpty());
    }
}
