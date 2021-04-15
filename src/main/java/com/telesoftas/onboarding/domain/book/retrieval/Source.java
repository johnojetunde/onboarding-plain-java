package com.telesoftas.onboarding.domain.book.retrieval;

import com.telesoftas.onboarding.domain.book.exception.BookRetrievalException;
import com.telesoftas.onboarding.domain.book.model.Book;

import java.util.Set;

public interface Source {

    Set<Book> retrieveByTitle(String title) throws BookRetrievalException;
}
