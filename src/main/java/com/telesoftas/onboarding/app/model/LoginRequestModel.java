package com.telesoftas.onboarding.app.model;

import com.telesoftas.onboarding.domain.librarian.model.Credential;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class LoginRequestModel {

    @NotNull(message = "Email is a required field")
    private String email;

    @NotNull(message = "Password is required")
    private String password;


    public Credential getCredential() {
        return Credential.builder()
            .email(this.getEmail())
            .password(this.getPassword())
            .build();
    }
}
