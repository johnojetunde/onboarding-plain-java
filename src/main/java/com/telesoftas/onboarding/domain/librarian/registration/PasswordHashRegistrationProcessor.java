package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.password.PasswordHasher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PasswordHashRegistrationProcessor implements RegistrationProcessor {

    private final PasswordHasher hasher;

    @Override
    public void process(Librarian librarian) {
        librarian.setPassword(hasher.hash(librarian.getPassword()));
    }
}
