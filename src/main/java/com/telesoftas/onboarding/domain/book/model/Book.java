package com.telesoftas.onboarding.domain.book.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Book {

    private Long id;

    @NotBlank(message = "Book title is required")
    private String title;

    private List<String> authors;

    private String publisher;

    @NonNull
    private String description;

    @NonNull
    private int pageCount;

    private String previewLink;

    private String language;

    private LocalDateTime timeCreated;

    private LocalDateTime timeModified;
}
