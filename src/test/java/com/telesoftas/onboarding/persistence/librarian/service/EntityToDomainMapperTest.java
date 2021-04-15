package com.telesoftas.onboarding.persistence.librarian.service;

import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.model.LibrarianFixture;
import com.telesoftas.onboarding.persistence.librarian.entity.LibrarianEntity;
import com.telesoftas.onboarding.persistence.librarian.fixtures.EntityFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityToDomainMapperTest {

    @Test
    void mapLibrarianEntityToLibrarianModel() {
        LibrarianEntity librarianEntity = EntityFixture.validLibrarianEntity();
        Librarian librarian = EntityToDomainMapper.toLibrarianModel(librarianEntity);

        assertEquals(librarianEntity.getFirstname(), librarian.getFirstname());
        assertEquals(librarianEntity.getLastname(), librarian.getLastname());
        assertEquals(librarianEntity.getEmail(), librarian.getEmail());
        assertEquals(librarianEntity.getPassword(), librarian.getPassword());
        assertEquals(librarianEntity.getTimeCreated(), librarian.getTimeCreated());
        assertEquals(librarianEntity.getTimeModified(), librarian.getTimeModified());
    }

    @Test
    void mapLibrarianModelToLibrarianEntity() {
        Librarian librarian = LibrarianFixture.validLibrarian();
        LibrarianEntity librarianEntity = EntityToDomainMapper.toLibrarianEntity(librarian);

        assertEquals(librarian.getFirstname(), librarianEntity.getFirstname());
        assertEquals(librarian.getLastname(), librarianEntity.getLastname());
        assertEquals(librarian.getEmail(), librarianEntity.getEmail());
        assertEquals(librarian.getPassword(), librarianEntity.getPassword());
        assertEquals(librarian.getTimeCreated(), librarianEntity.getTimeCreated());
        assertEquals(librarian.getTimeModified(), librarianEntity.getTimeModified());
    }
}
