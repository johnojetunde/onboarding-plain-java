package com.telesoftas.onboarding.domain.book.retrieval;

import com.telesoftas.onboarding.domain.book.exception.BookRetrievalException;
import com.telesoftas.onboarding.domain.book.exception.BookRetrievalViolationException;
import com.telesoftas.onboarding.domain.book.exception.BookStorageException;
import com.telesoftas.onboarding.domain.book.model.Book;
import com.telesoftas.onboarding.domain.book.validator.BookValidator;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class BookRetrieverByTitle implements BookRetriever {

    private final BookValidator validator;

    private final Source source;

    private final BookStorage storage;

    @Override
    public Set<Book> retrieve(@NonNull String title) throws BookRetrievalViolationException, BookRetrievalException {
        try {
            validator.byTitle(title);

            Set<Book> books = source.retrieveByTitle(title);

            storage.save(books);

            return books;
        } catch (ViolationException e) {
            throw new BookRetrievalViolationException(e.getMessage(), e.getViolations());
        } catch (BookStorageException e) {
            throw new BookRetrievalException("Error encountered while saving the books retrived from source", e);
        } catch (Exception e) {
            throw new BookRetrievalException("Book Search exception", e);
        }
    }

}
