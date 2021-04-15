package com.telesoftas.onboarding.app.factory;

import com.telesoftas.onboarding.domain.librarian.registration.LibrarianEmailExistence;
import lombok.experimental.UtilityClass;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.Validation;
import javax.validation.Validator;

@UtilityClass
public class ValidatorFactory {

    public static Validator getValidator() {
        return Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();

    }

    public static Validator getValidatorWithUniqueEmailContext(LibrarianEmailExistence emailExisting) {
        return Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory()
            .usingContext().constraintValidatorFactory(new ConstraintValidatorFactoryImpl(emailExisting))
            .getValidator();
    }
}
