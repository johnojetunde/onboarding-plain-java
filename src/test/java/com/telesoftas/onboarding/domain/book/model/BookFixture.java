package com.telesoftas.onboarding.domain.book.model;

import java.util.Collections;

public class BookFixture {

    public static Book valid() {
        return Book.builder()
            .authors(Collections.singletonList("Nerijus"))
            .pageCount(10)
            .publisher("Telesoftas")
            .description("Onboarding Project")
            .title("Onboarding Project")
            .language("eng")
            .build();
    }
}
