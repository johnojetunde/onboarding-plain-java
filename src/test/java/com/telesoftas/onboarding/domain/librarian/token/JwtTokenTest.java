package com.telesoftas.onboarding.domain.librarian.token;

import com.telesoftas.onboarding.domain.librarian.exception.ExpireTokenException;
import com.telesoftas.onboarding.domain.librarian.exception.MalformedTokenException;
import com.telesoftas.onboarding.domain.librarian.exception.TokenGenerationException;
import com.telesoftas.onboarding.domain.librarian.exception.TokenVerificationException;
import com.telesoftas.onboarding.domain.librarian.model.AccessToken;
import com.telesoftas.onboarding.domain.librarian.model.LibrarianFixture;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static java.util.Date.from;
import static org.junit.jupiter.api.Assertions.*;


class JwtTokenTest {

    private static JwtToken jwtToken;

    private static Key key;

    private static long expiryLimitMinutes;

    private static JwtClock defaultClock;

    private static JwtClock fixedClock;


    @BeforeEach
    void setUp() {
        expiryLimitMinutes = 30L;

        key = Keys.hmacShaKeyFor(("$2a$10$A.0cPjEOQ26b2hR6lpoHDeStmoK9IGiiMKRxdEMHS4CkwSw3norJu").getBytes());

        defaultClock = new JwtClock(Clock.systemDefaultZone());

        fixedClock = new JwtClock(Clock.fixed(Instant.parse("2017-04-29T10:15:30.00Z"), ZoneId.systemDefault()));

        jwtToken = new JwtToken(key, Duration.ofMinutes(expiryLimitMinutes), defaultClock);
    }

    @Test
    void should_generate_a_valid_token_successfully() throws TokenGenerationException {
        AccessToken authLibrarian = jwtToken.generate(LibrarianFixture.validLibrarian());

        assertNotNull(authLibrarian.getToken());
    }


    @Test
    void should_verify_token_successfully() throws MalformedTokenException, TokenVerificationException, ExpireTokenException {

        jwtToken = new JwtToken(key, Duration.ofMinutes(expiryLimitMinutes), fixedClock);

        jwtToken.verify("eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJKb2huIE9qZXR1bmRlIiwiZXhwIjoxNDkzNDYyNzMwfQ.ZrpPPA6tCGt7qNvdIYWsrFrU9rJ8UbNApjgzo6fZETBlZ0WVKwzuLzBJKDrrmEac");
    }


    @Test
    void should_throw_an_expired_token_exception() throws TokenGenerationException {
        jwtToken = new JwtToken(key, Duration.ofMinutes(expiryLimitMinutes), fixedClock);

        AccessToken authLibrarian = jwtToken.generate(LibrarianFixture.validLibrarian());

        jwtToken = new JwtToken(key, Duration.ofMinutes(expiryLimitMinutes), defaultClock);

        assertThrows(ExpireTokenException.class, () -> jwtToken.verify(authLibrarian.getToken()));
    }

    @Test
    void should_throw_an_exception_when_token_is_malformed() {
        assertThrows(TokenVerificationException.class, () ->
            jwtToken.verify("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxsb0BoZWxsby5jb20iLCJleHAiOjE1NzI5NDc4ODd9.Onrn3b2v3JlMynrEMJc8V_aJuPVt-5izoisODAFbq-Asdsfdfdfdfdfdfdfdfdfd"));
    }

    @Test
    void should_throw_an_exception_when_key_is_incorrect() throws TokenGenerationException {

        AccessToken authLibrarian = jwtToken.generate(LibrarianFixture.validLibrarian());

        jwtToken = new JwtToken(Keys.hmacShaKeyFor(("$2a$10$A.0cPjEOQ26b2hR6lpoHDeStmoK9IGiiMKRxdEMHS4CkwSw3norJu111111").getBytes()), Duration.ofMinutes(expiryLimitMinutes), defaultClock);

        assertThrows(TokenVerificationException.class, () -> jwtToken.verify(authLibrarian.getToken()), "Unable to verify jwt token");
    }

    @Test
    void should_return_a_future_date_as_expiry_date() {

        jwtToken = new JwtToken(key, Duration.ofMinutes(expiryLimitMinutes), fixedClock);

        Date expectedExpiryDate = from(fixedClock.systemClock().instant().plus(Duration.ofMinutes(expiryLimitMinutes)));

        Date expirationDate = jwtToken.getExpirationDate();

        assertEquals(expectedExpiryDate, expirationDate);
    }
}
