package com.telesoftas.onboarding.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibrarianApiResponseModel {

    private String firstname;
    private String lastname;
    private String email;
}
