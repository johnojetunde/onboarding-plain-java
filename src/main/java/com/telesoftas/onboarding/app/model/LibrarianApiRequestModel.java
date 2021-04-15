package com.telesoftas.onboarding.app.model;

import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Builder
public class LibrarianApiRequestModel {

    @NotNull(message = "Firstname is a required field")
    private String firstname;

    @NotNull(message = "Lastname is a required field")
    private String lastname;

    @NotNull(message = "Email is a required field")
    private String email;

    @NotNull(message = "Password is required")
    private String password;


    public Librarian getLibrarian() {
        return Librarian.builder()
            .firstname(this.getFirstname())
            .lastname(this.getLastname())
            .email(this.getEmail())
            .password(this.getPassword())
            .build();
    }
}
