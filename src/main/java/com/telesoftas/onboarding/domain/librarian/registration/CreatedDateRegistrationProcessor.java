package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CreatedDateRegistrationProcessor implements RegistrationProcessor {

    private final Clock clock;

    @Override
    public void process(Librarian librarian) {
        librarian.setTimeCreated(LocalDateTime.now(clock));
    }
}
