package com.telesoftas.onboarding.domain.librarian.model;

import java.time.LocalDateTime;

public class LibrarianFixture {

    public static Librarian validLibrarian(){
        return Librarian.builder()
            .firstname("John")
            .lastname("Ojetunde")
            .password("$2y$12$zZN78i68bh20AT2Twt40NuxumBrNyLvH8VBBvYPf9QzhYwXdlxDei")
            .email("hello@hello.com")
            .timeCreated(LocalDateTime.now())
            .timeModified(LocalDateTime.now())
            .build();
    }

    public static Librarian validLibrarianWithPlainPassword(){
        return Librarian.builder()
            .firstname("John")
            .lastname("Ojetunde")
            .password("telesoftas")
            .email("hello@hello.com")
            .timeCreated(LocalDateTime.now())
            .timeModified(LocalDateTime.now())
            .build();
    }
}
