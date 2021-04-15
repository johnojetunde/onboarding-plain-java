package com.telesoftas.onboarding.domain.librarian.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Credential {
    @NonNull
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NonNull
    @NotBlank(message = "Password is required")
    private String password;
}
