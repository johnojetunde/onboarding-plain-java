package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.exception.EmptyTokenException;

import javax.servlet.http.HttpServletRequest;

public interface TokenExtractor {
    String extract(HttpServletRequest request) throws EmptyTokenException;
}
