package com.telesoftas.onboarding.domain.librarian.password;

public interface PasswordHasher {
    String hash(String plainPassword);
}
