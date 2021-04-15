package com.telesoftas.onboarding.app.exception;

import com.telesoftas.onboarding.domain.exception.ViolationException;

import java.util.List;
import java.util.Map;

public class AppViolationException extends ViolationException {

    public AppViolationException(String message, Map<String, List<String>> violation) {
        super(message, violation);
    }
}
