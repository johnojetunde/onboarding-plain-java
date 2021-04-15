package com.telesoftas.onboarding.googlebook;

import com.telesoftas.onboarding.googlebook.exception.GoogleBookAPIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class GoogleBookRetrieverTest {

    private BookRetriever bookRetriever;

    @BeforeEach
    void setUp() {
        bookRetriever = new BookRetriever(1);
    }

    @Test
    void retrieveByName() throws GoogleBookAPIException {

        bookRetriever.byTitle("The subtle art of not giving a fuck", 10);
    }

    @Test
    void should_throw_exception_when_response_status_not_200() {
        bookRetriever = new BookRetriever(10, "https://www.googleapis.com/books/v1/volumesscvdfbgefbbdfgb");

        try {
            bookRetriever.byTitle("name", 10);
            fail();
        } catch (GoogleBookAPIException e) {
            assertEquals("Unexpected response status: 404", e.getMessage());
        }
    }

}
