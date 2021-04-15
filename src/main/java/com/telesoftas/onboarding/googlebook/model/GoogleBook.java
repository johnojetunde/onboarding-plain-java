package com.telesoftas.onboarding.googlebook.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GoogleBook {
    private String title;

    private List<String> authors;

    private String publisher;

    private String description;

    private int pageCount;

    private String previewLink;

    private String language;
}
