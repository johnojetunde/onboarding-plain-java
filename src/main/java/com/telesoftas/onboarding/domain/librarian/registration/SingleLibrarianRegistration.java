package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.domain.exception.ViolationException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianSaveException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianViolationException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.validator.RegistrationGroup;
import com.telesoftas.onboarding.domain.validator.ModelValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SingleLibrarianRegistration implements Registration {

    private final Storage storage;

    private final List<RegistrationProcessor> processors;

    private final ModelValidator<Librarian> validator;

    @Override
    public void register(Librarian librarian) throws LibrarianServiceException, LibrarianViolationException {
        try {

            validator.validate(librarian, RegistrationGroup.class);

            processors.forEach(p -> p.process(librarian));

            storage.save(librarian);

        } catch (ViolationException e) {
            throw new LibrarianViolationException(e.getMessage(), e.getViolations());
        } catch (LibrarianSaveException e) {
            throw new LibrarianServiceException("Unable to complete registration request", e);
        }
    }

}
