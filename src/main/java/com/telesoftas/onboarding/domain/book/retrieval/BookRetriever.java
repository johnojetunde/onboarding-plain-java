package com.telesoftas.onboarding.domain.book.retrieval;

import com.telesoftas.onboarding.domain.book.exception.BookRetrievalException;
import com.telesoftas.onboarding.domain.book.exception.BookRetrievalViolationException;
import com.telesoftas.onboarding.domain.book.model.Book;

import java.util.Set;

public interface BookRetriever {

    Set<Book> retrieve(String title) throws BookRetrievalViolationException, BookRetrievalException;
}
