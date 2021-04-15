package com.telesoftas.onboarding.domain.librarian.model;

public class CredentialFixture {
    public static Credential valid() {
        return Credential.builder()
            .password("password")
            .email("hello@hello.com")
            .build();
    }

}
