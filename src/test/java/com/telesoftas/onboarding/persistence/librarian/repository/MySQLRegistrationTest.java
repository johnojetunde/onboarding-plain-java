package com.telesoftas.onboarding.persistence.librarian.repository;

import com.telesoftas.onboarding.app.database.DatabaseConnection;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianSaveException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianUniqueEmailCheckException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.model.LibrarianFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MySQLRegistrationTest {

    private MySQLRegistration storageAndEmailExistence;

    private Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        connection = DatabaseConnection.initializeDatabaseConnection(
            "jdbc:mysql://127.0.0.1:3306/onboarding?user=user&password=password");
        connection.setAutoCommit(false);

        storageAndEmailExistence = new MySQLRegistration(connection);

    }

    @Test
    void should_return_false_if_librarian_email_does_not_exist() throws LibrarianUniqueEmailCheckException {

        assertFalse(storageAndEmailExistence.isEmailExisting("hello@telesoftas.com"));
    }

    @Test
    void should_return_true_if_librarian_email_exists() throws LibrarianUniqueEmailCheckException, LibrarianSaveException {
        Librarian librarian = LibrarianFixture.validLibrarian();
        librarian.setEmail("hello@teleosftas.com");

        storageAndEmailExistence.save(librarian);

        assertTrue(storageAndEmailExistence.isEmailExisting("hello@teleosftas.com"));
    }

    @Test
    void should_throw_an_exception_if_saving_fails() {
        Librarian librarian = LibrarianFixture.validLibrarian();
        librarian.setEmail("hello@teleosftas.com");

        assertThrows(LibrarianSaveException.class, () -> {
            storageAndEmailExistence.save(librarian);
            storageAndEmailExistence.save(librarian);
        });
    }


    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
    }
}
