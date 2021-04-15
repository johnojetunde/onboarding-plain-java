package com.telesoftas.onboarding.domain.book.retrieval;

import com.telesoftas.onboarding.domain.book.exception.BookStorageException;
import com.telesoftas.onboarding.domain.book.model.Book;

import java.util.Set;

public interface BookStorage {

    void save(Set<Book> books) throws BookStorageException;

}
