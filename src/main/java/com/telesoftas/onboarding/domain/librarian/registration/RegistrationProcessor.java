package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.domain.librarian.model.Librarian;

public interface RegistrationProcessor {
    void process(Librarian librarian);
}
