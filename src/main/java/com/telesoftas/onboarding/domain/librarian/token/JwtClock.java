package com.telesoftas.onboarding.domain.librarian.token;


import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.util.Date;

import static java.util.Date.from;

@RequiredArgsConstructor
public class JwtClock implements io.jsonwebtoken.Clock {

    private final Clock clock;

    @Override
    public Date now() {
        return from(clock.instant());
    }

    Clock systemClock() {
        return clock;
    }
}
