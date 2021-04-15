package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.app.exception.EmptyTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BearerTokenExtractorTest {

    private BearerTokenExtractor tokenExtractor;

    @BeforeEach
    void setUp() {
        tokenExtractor = new BearerTokenExtractor();
    }

    @Test
    void should_extract_token_from_header() throws EmptyTokenException {
        String token = tokenExtractor.fromHeader("Bearer ofkdghjdfjdbgjfdhgjbdjgfbdjgfbdjgdfg");

        assertEquals("ofkdghjdfjdbgjfdhgjbdjgfbdjgfbdjgdfg", token);
    }

    @Test
    void should_throw_an_empty_token_exception_when_token_is_absent() {

        assertThrows(EmptyTokenException.class, () -> tokenExtractor.fromHeader("Bearer  "), "Request does not contain token");

        assertThrows(EmptyTokenException.class, () -> tokenExtractor.fromHeader(""), "Request does not contain token");
    }
}
