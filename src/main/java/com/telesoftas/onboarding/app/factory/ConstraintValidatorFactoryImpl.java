package com.telesoftas.onboarding.app.factory;

import com.telesoftas.onboarding.domain.librarian.registration.LibrarianEmailExistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public class ConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {

    private final LibrarianEmailExistence emailExisting;

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> aClass) {
        T instance = null;

        try {
            instance = aClass.getDeclaredConstructor().newInstance();
            if (LibrarianEmailExistingAwareValidator.class.isAssignableFrom(aClass)) {
                LibrarianEmailExistingAwareValidator validator = (LibrarianEmailExistingAwareValidator) instance;
                validator.setEmailExisting(emailExisting);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error getting instance of StorageAwareValidator", e);
        }

        return instance;
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> constraintValidator) {
    }
}
