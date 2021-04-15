package com.telesoftas.onboarding.domain.librarian.registration;

import com.telesoftas.onboarding.domain.librarian.exception.LibrarianSaveException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;

public interface Storage {
    void save(Librarian librarian) throws LibrarianSaveException;
}
