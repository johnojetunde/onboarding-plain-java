package com.telesoftas.onboarding.persistence.librarian.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LibrarianEntity {
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private LocalDateTime timeCreated;

    private LocalDateTime timeModified;
}
