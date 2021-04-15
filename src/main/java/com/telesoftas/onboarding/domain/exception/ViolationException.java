package com.telesoftas.onboarding.domain.exception;

import lombok.Getter;

import java.util.List;
import java.util.Map;

public class ViolationException extends Exception{

    @Getter
    private final Map<String, List<String>> violations;

    public ViolationException(String message, Map<String, List<String>> violations) {
        super(message);
        this.violations = violations;
    }

}
