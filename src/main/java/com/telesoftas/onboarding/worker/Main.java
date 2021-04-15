package com.telesoftas.onboarding.worker;

import com.telesoftas.onboarding.app.factory.ValidatorFactory;
import com.telesoftas.onboarding.app.service.GoogleBookSource;
import com.telesoftas.onboarding.domain.book.exception.BookRetrievalException;
import com.telesoftas.onboarding.domain.book.exception.BookRetrievalViolationException;
import com.telesoftas.onboarding.domain.book.model.Book;
import com.telesoftas.onboarding.domain.book.retrieval.BookRetriever;
import com.telesoftas.onboarding.domain.book.retrieval.BookRetrieverByTitle;
import com.telesoftas.onboarding.domain.book.validator.BookValidator;
import lombok.extern.java.Log;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

@Log
public class Main {

    private static final int MAX_PAGES = 5;

    public static void main(String[] args) {
        try {
            final Properties properties = new Properties();
            properties.load(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("worker.properties")));

            Validator validator = ValidatorFactory.getValidator();
            BookValidator bookValidator = new BookValidator(validator);

            com.telesoftas.onboarding.googlebook.BookRetriever googleBookRetriever = new com.telesoftas.onboarding.googlebook.BookRetriever(MAX_PAGES);
            GoogleBookSource bookSource = new GoogleBookSource(googleBookRetriever);

            BookRetrieverByTitle bookRetrieval = new BookRetrieverByTitle(bookValidator, bookSource, books -> System.out.println("My name"));

            retrieveBooks(bookRetrieval);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error starting the worker main", e);
        }
    }


    private static void retrieveBooks(BookRetriever bookRetriever) {
        try {
            log.info("------- Retrieving books from source --------");

            Set<Book> books = bookRetriever.retrieve("a");

            log.info(String.format("---------%d books retrieved as at %s---------", books.size(), LocalDateTime.now().toString()));

        } catch (BookRetrievalException | BookRetrievalViolationException e) {
            log.log(Level.SEVERE, "Error encountered when trying to start the BookSearchWorker", e);
        }
    }
}
