package com.telesoftas.onboarding.app.service;

import com.telesoftas.onboarding.domain.book.exception.BookRetrievalException;
import com.telesoftas.onboarding.domain.book.model.Book;
import com.telesoftas.onboarding.domain.book.retrieval.Source;
import com.telesoftas.onboarding.googlebook.BookRetriever;
import com.telesoftas.onboarding.googlebook.exception.GoogleBookAPIException;
import com.telesoftas.onboarding.googlebook.model.GoogleBook;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoogleBookSource implements Source {

    private final BookRetriever retrieval;

    private static Book fromGoogleBook(GoogleBook gBook) {
        return Book.builder()
            .title(gBook.getTitle())
            .description(gBook.getDescription())
            .language(gBook.getLanguage())
            .publisher(gBook.getPublisher())
            .pageCount(gBook.getPageCount())
            .authors(gBook.getAuthors())
            .previewLink(gBook.getPreviewLink())
            .build();
    }

    @Override
    public Set<Book> retrieveByTitle(@NonNull String title) throws BookRetrievalException {
        try {
            final int PAGE_SIZE = 40;
            Set<GoogleBook> googleBooks = retrieval.byTitle(title, PAGE_SIZE);

            return googleBooks.stream().map(GoogleBookSource::fromGoogleBook).collect(Collectors.toSet());

        } catch (GoogleBookAPIException e) {
            throw new BookRetrievalException(e);
        }
    }
}
