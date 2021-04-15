package com.telesoftas.onboarding.domain.librarian.validator;

import com.telesoftas.onboarding.app.factory.LibrarianEmailExistingAwareValidator;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianUniqueEmailCheckException;
import com.telesoftas.onboarding.domain.librarian.registration.LibrarianEmailExistence;
import lombok.extern.java.Log;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.logging.Level;

@Log
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>, LibrarianEmailExistingAwareValidator {

    private LibrarianEmailExistence emailExisting;

    @Override
    public void setEmailExisting(LibrarianEmailExistence emailExisting) {
        this.emailExisting = emailExisting;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        //there is nothing to be initialized here for now
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        try {

            return !emailExisting.isEmailExisting(email);

        } catch (LibrarianUniqueEmailCheckException e) {
            log.log(Level.SEVERE, "Error validating librarian email uniqueness", e);
        }

        return false;
    }
}
