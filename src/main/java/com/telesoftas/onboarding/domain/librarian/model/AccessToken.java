package com.telesoftas.onboarding.domain.librarian.model;

import lombok.Data;
import lombok.Value;


@Data
@Value
public class AccessToken {

    private Librarian librarian;

    private String token;
}
