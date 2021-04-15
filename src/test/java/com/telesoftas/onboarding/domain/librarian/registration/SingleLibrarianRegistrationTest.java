package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.app.service.ValidatorStub;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianViolationException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.model.LibrarianFixture;
import com.telesoftas.onboarding.domain.librarian.stubs.StorageStub;
import com.telesoftas.onboarding.domain.librarian.validator.LibrarianModelValidator;
import com.telesoftas.onboarding.domain.validator.ModelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SingleLibrarianRegistrationTest {

    private SingleLibrarianRegistration registration;

    private ModelValidator<Librarian> modelValidator;

    private Storage storage;


    @BeforeEach
    void setUp() {
        Validator validator = ValidatorStub.validatorWithUniqueEmailContext(false, false);

        modelValidator = new LibrarianModelValidator(validator);

        storage = StorageStub.newStorage(true);

        registration = new SingleLibrarianRegistration(storage, Collections.emptyList(), modelValidator);

    }

    @Test
    void exception_is_thrown_when_librarian_validation_has_some_violations() {
        Librarian librarian = LibrarianFixture.validLibrarian();

        modelValidator = new LibrarianModelValidator(ValidatorStub.validatorWithUniqueEmailContext(true, false));
        registration = new SingleLibrarianRegistration(storage, Collections.emptyList(), modelValidator);

        assertThrows(LibrarianViolationException.class, () ->
            registration.register(librarian), "Librarian with this email exists in the database"
        );
    }


    @Test
    void exception_is_thrown_when_librarian_storage_save_fails() {
        Librarian librarian = LibrarianFixture.validLibrarian();

        registration = new SingleLibrarianRegistration(
            StorageStub.newStorage(false),
            Collections.emptyList(), modelValidator);

        assertThrows(LibrarianServiceException.class, () ->
            registration.register(librarian), "Unable to complete registration request"
        );
    }

    @Test
    void registration_is_successful_and_processors_are_called() throws LibrarianServiceException, LibrarianViolationException {
        Librarian librarian = LibrarianFixture.validLibrarianWithPlainPassword();
        librarian.setTimeCreated(null);
        librarian.setPassword("johndoe1242");

        List<RegistrationProcessor> processors = new ArrayList<>();
        processors.add(new RegistrationProcessorStub());

        SingleLibrarianRegistration registration = new SingleLibrarianRegistration(
            StorageStub.newStorage(true),
            processors, modelValidator);

        registration.register(librarian);

        assertEquals("RegistrationProcessorStubPasswordHash", librarian.getPassword());
        assertNotNull(librarian.getTimeCreated());
    }


    static class RegistrationProcessorStub implements RegistrationProcessor {

        @Override
        public void process(Librarian librarian) {
            librarian.setPassword("RegistrationProcessorStubPasswordHash");
            librarian.setTimeCreated(LocalDateTime.now());
        }
    }


}
