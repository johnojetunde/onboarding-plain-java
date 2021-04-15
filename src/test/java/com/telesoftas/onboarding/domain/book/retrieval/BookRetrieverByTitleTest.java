package com.telesoftas.onboarding.domain.book.retrieval;

import com.telesoftas.onboarding.app.service.ValidatorStub;
import com.telesoftas.onboarding.domain.book.exception.BookRetrievalException;
import com.telesoftas.onboarding.domain.book.exception.BookRetrievalViolationException;
import com.telesoftas.onboarding.domain.book.model.Book;
import com.telesoftas.onboarding.domain.book.model.BookFixture;
import com.telesoftas.onboarding.domain.book.stubs.BookValidatorStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookRetrieverByTitleTest {

    private BookRetrieverByTitle bookRetrieval;

    @BeforeEach
    void setUp() {
        bookRetrieval = new BookRetrieverByTitle(
            new BookValidatorStub(ValidatorStub.validator(), false),
            source(false), books -> System.out.println("Saving is successful"));
    }


    @Test
    void should_retrieve_books_successfully() throws BookRetrievalException, BookRetrievalViolationException {

        bookRetrieval.retrieve("Clean Code");
    }

    @Test
    void should_throw_an_exception_when_validation_fails() throws BookRetrievalException {
        bookRetrieval = new BookRetrieverByTitle(
            new BookValidatorStub(ValidatorStub.validator(), true),
            source(false), books -> System.out.println("Saving is successful"));

        try {
            bookRetrieval.retrieve("");
        } catch (BookRetrievalViolationException e) {
            assertTrue(e.getViolations().containsKey("title"));
            assertTrue(e.getViolations().containsValue("Book title is required"));
        }
    }


    @Test
    void should_throw_an_exception_when_APIDatasource_call_fails() throws BookRetrievalViolationException {
        try {
            bookRetrieval = new BookRetrieverByTitle(
                new BookValidatorStub(ValidatorStub.validator(), false),
                source(true), books -> System.out.println("Saving is successful")
            );

            bookRetrieval.retrieve("Clean code");
        } catch (BookRetrievalException e) {
            assertEquals("Book Search exception", e.getMessage());
        }
    }


    Source source(boolean throwException) {
        return name -> {
            if (throwException) {
                throw new BookRetrievalException("Error retrieving books from source");
            }

            return new HashSet<Book>() {{
                BookFixture.valid();
            }};
        };
    }

}
