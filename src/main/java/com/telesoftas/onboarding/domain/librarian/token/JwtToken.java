package com.telesoftas.onboarding.domain.librarian.token;

import com.telesoftas.onboarding.domain.librarian.exception.ExpireTokenException;
import com.telesoftas.onboarding.domain.librarian.exception.MalformedTokenException;
import com.telesoftas.onboarding.domain.librarian.exception.TokenGenerationException;
import com.telesoftas.onboarding.domain.librarian.exception.TokenVerificationException;
import com.telesoftas.onboarding.domain.librarian.model.AccessToken;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

import static java.util.Date.from;


@RequiredArgsConstructor
public class JwtToken implements TokenGenerator, TokenVerification {

    private final Key key;

    private final Duration duration;

    private final JwtClock clock;

    @Override
    public AccessToken generate(@NonNull final Librarian librarian) throws TokenGenerationException {
        try {
            String token = Jwts.builder()
                .setSubject(librarian.getEmail())
                .setExpiration(getExpirationDate())
                .signWith(key)
                .compact();

            return new AccessToken(librarian, token);
        } catch (Exception e) {
            throw new TokenGenerationException("Error generating jwt token", e);
        }
    }


    @Override
    public void verify(@NonNull String token) throws ExpireTokenException, MalformedTokenException, TokenVerificationException {
        try {

            Jwts.parser().setClock(clock)
                .setSigningKey(key)
                .parseClaimsJws(token);

        } catch (ExpiredJwtException e) {
            throw new ExpireTokenException(e);
        } catch (MalformedJwtException e) {
            throw new MalformedTokenException(e);
        } catch (Exception e) {
            throw new TokenVerificationException("Unable to verify jwt token", e);
        }
    }


    Date getExpirationDate() {
        return from(clock.systemClock().instant().plus(duration));
    }
}
