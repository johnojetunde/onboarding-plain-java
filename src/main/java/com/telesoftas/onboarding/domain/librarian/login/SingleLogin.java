package com.telesoftas.onboarding.domain.librarian.login;

import com.telesoftas.onboarding.domain.exception.ViolationException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.exception.PasswordMatchException;
import com.telesoftas.onboarding.domain.librarian.exception.TokenGenerationException;
import com.telesoftas.onboarding.domain.librarian.model.AccessToken;
import com.telesoftas.onboarding.domain.librarian.model.Credential;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.password.PasswordMatcher;
import com.telesoftas.onboarding.domain.librarian.token.TokenGenerator;
import com.telesoftas.onboarding.domain.librarian.validator.CredentialModelValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SingleLogin implements Login {

    private final CredentialModelValidator validator;

    private final LibrarianFindByEmail storage;

    private final PasswordMatcher passwordMatcher;

    private final TokenGenerator tokenGenerator;

    @Override
    public AccessToken login(@NonNull Credential credential) throws ViolationException, LibrarianServiceException {
        try {
            validator.validate(credential);

            Librarian librarian = storage.findByEmail(credential.getEmail())
                .orElseThrow(() -> new LibrarianServiceException("Librarian with email does not exist"));

            passwordMatcher.match(credential.getPassword(), librarian.getPassword());

            return tokenGenerator.generate(librarian);
        } catch (ViolationException e) {
            throw new ViolationException(e.getMessage(), e.getViolations());
        } catch (PasswordMatchException e) {
            throw new LibrarianServiceException("Librarian password incorrect", e);
        } catch (TokenGenerationException e) {
            throw new LibrarianServiceException("Librarian login was not successful", e);
        }
    }

}
