package com.telesoftas.onboarding.persistence.librarian.fixtures;

import com.telesoftas.onboarding.persistence.librarian.entity.LibrarianEntity;

public class EntityFixture {

    public static LibrarianEntity validLibrarianEntity() {
        return LibrarianEntity.builder()
            .firstname("John")
            .lastname("Ojetunde")
            .password("$2y$12$zZN78i68bh20AT2Twt40NuxumBrNyLvH8VBBvYPf9QzhYwXdlxDei")
            .email("hello@hello.com")
            .build();
    }
}
