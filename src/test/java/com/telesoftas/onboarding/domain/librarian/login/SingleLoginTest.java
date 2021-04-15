package com.telesoftas.onboarding.domain.librarian.login;

import com.telesoftas.onboarding.app.service.ValidatorStub;
import com.telesoftas.onboarding.domain.exception.ViolationException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.exception.TokenGenerationException;
import com.telesoftas.onboarding.domain.librarian.model.*;
import com.telesoftas.onboarding.domain.librarian.stubs.LibrarianFindByEmailStub;
import com.telesoftas.onboarding.domain.librarian.stubs.PasswordMatcherStub;
import com.telesoftas.onboarding.domain.librarian.validator.CredentialModelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleLoginTest {

    private SingleLogin librarianLogin;

    private CredentialModelValidator modelValidator;

    private Librarian librarian;

    @BeforeEach
    void setUp() {
        Validator validator = ValidatorStub.validator();

        modelValidator = new CredentialModelValidator(validator);

        librarian = LibrarianFixture.validLibrarian();

        librarianLogin = new SingleLogin(modelValidator, LibrarianFindByEmailStub.newFindByEmail(librarian, false),
            PasswordMatcherStub.newPasswordMatcher(true), librarian -> new AccessToken(librarian, "token"));

    }

    @Test
    void should_throw_an_exception_when_librarian_validation_has_some_violations() {
        Credential credential = CredentialFixture.valid();
        credential.setEmail("helloemail");


        assertThrows(ViolationException.class, () ->
            librarianLogin.login(credential), "Email is invalid"
        );
    }


    @Test
    void should_throw_an_exception_when_librarian_with_email_does_not_exist() {
        Credential credential = CredentialFixture.valid();

        librarianLogin = new SingleLogin(modelValidator, LibrarianFindByEmailStub.newFindByEmail(null, false),
            PasswordMatcherStub.newPasswordMatcher(true), librarian -> new AccessToken(librarian, "token"));

        assertThrows(LibrarianServiceException.class, () ->
            librarianLogin.login(credential), "Librarian with email does not exist"
        );
    }


    @Test
    void should_throw_an_exception_when_librarian_findByEmail_fails() {
        Credential credential = CredentialFixture.valid();

        librarianLogin = new SingleLogin(modelValidator, LibrarianFindByEmailStub.newFindByEmail(null, true),
            PasswordMatcherStub.newPasswordMatcher(true), librarian -> new AccessToken(librarian, "token"));

        assertThrows(LibrarianServiceException.class, () ->
            librarianLogin.login(credential), "Unable to retrieve librarian by email"
        );
    }


    @Test
    void should_throw_an_exception_when_password_does_not_match() {
        Credential credential = CredentialFixture.valid();

        librarianLogin = new SingleLogin(modelValidator, LibrarianFindByEmailStub.newFindByEmail(librarian, false),
            PasswordMatcherStub.newPasswordMatcher(false), librarian -> new AccessToken(librarian, "token"));

        assertThrows(LibrarianServiceException.class, () ->
            librarianLogin.login(credential), "Librarian password incorrect"
        );
    }


    @Test
    void should_throw_an_exception_when_token_generation_fails() {
        Credential credential = CredentialFixture.valid();

        librarianLogin = new SingleLogin(modelValidator, LibrarianFindByEmailStub.newFindByEmail(librarian, false),
            PasswordMatcherStub.newPasswordMatcher(true), librarian -> {
            throw new TokenGenerationException("Unable to generate jwt token");
        });

        assertThrows(LibrarianServiceException.class, () ->
            librarianLogin.login(credential), "Librarian login was not successful"
        );
    }


    @Test
    void should_login_the_librarian_successfully() throws ViolationException, LibrarianServiceException {
        Credential credential = CredentialFixture.valid();

        librarianLogin = new SingleLogin(modelValidator, LibrarianFindByEmailStub.newFindByEmail(librarian, false),
            PasswordMatcherStub.newPasswordMatcher(true), librarian -> new AccessToken(librarian, "MyHashedTokenTokenTokenTokeneyidhfbdfbdjfb272e39referefefef"));

        AccessToken accessToken = librarianLogin.login(credential);

        assertEquals("MyHashedTokenTokenTokenTokeneyidhfbdfbdjfb272e39referefefef", accessToken.getToken());
        assertEquals(librarian, accessToken.getLibrarian());
    }
}
