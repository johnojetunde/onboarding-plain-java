package com.telesoftas.onboarding.persistence.librarian.repository;

import com.telesoftas.onboarding.app.database.DatabaseConnection;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianSaveException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.model.LibrarianFixture;
import com.telesoftas.onboarding.domain.librarian.registration.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class LibrarianFindByEmailMySQLTest {

    private LibrarianFindByEmailMySQL findByEmailMySQL;

    private Connection connection;

    private Storage storage;

    @BeforeEach
    void setUp() throws SQLException {

        connection = DatabaseConnection.initializeDatabaseConnection(
            "jdbc:mysql://127.0.0.1:3306/onboarding?user=user&password=password");
        connection.setAutoCommit(false);

        findByEmailMySQL = new LibrarianFindByEmailMySQL(connection);

        storage = new MySQLRegistration(connection);
    }

    @Test
    void should_return_valid_existing_librarian() throws LibrarianSaveException, LibrarianServiceException {
        Librarian librarian = LibrarianFixture.validLibrarian();
        librarian.setEmail("test@telesoftas.com");

        storage.save(librarian);

        Optional<Librarian> librarianProfile = findByEmailMySQL.findByEmail("test@telesoftas.com");

        assertTrue(librarianProfile.isPresent());
        assertNotNull(librarianProfile.get().getId());

        assertEquals(librarian.getFirstname(), librarianProfile.get().getFirstname());
        assertEquals(librarian.getLastname(), librarianProfile.get().getLastname());
        assertEquals(librarian.getEmail(), librarianProfile.get().getEmail());
        assertEquals(librarian.getPassword(), librarianProfile.get().getPassword());
    }


    @Test
    void should_return_empty_optional_librarian_when_librarian_does_not_exist() throws LibrarianServiceException {
        assertFalse(findByEmailMySQL.findByEmail("does_not_exist@existence.com").isPresent());
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
    }
}
