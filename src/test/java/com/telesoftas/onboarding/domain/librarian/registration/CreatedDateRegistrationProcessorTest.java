package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.model.LibrarianFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatedDateRegistrationProcessorTest {

    private CreatedDateRegistrationProcessor processor;

    private Clock clock;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(Instant.parse("2018-04-29T10:15:30.00Z"), ZoneId.systemDefault());
        processor = new CreatedDateRegistrationProcessor(clock);
    }

    @Test
    void should_furnish_librarian_model_created_date_with_todays_date() {
        Librarian librarian = LibrarianFixture.validLibrarian();
        librarian.setTimeCreated(null);

        LocalDateTime now = LocalDateTime.now(clock);

        processor.process(librarian);

        assertEquals(now, librarian.getTimeCreated());
    }

}
