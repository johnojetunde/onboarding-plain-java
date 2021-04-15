package com.telesoftas.onboarding.domain.librarian.stubs;

import com.telesoftas.onboarding.domain.librarian.exception.LibrarianSaveException;
import com.telesoftas.onboarding.domain.librarian.registration.Storage;

public class StorageStub {

    public static Storage newStorage(boolean isSaved) {
        return storage -> {
            if (!isSaved) {
                throw new LibrarianSaveException("Unable to save librarian data", new Exception());
            }
        };
    }
}
