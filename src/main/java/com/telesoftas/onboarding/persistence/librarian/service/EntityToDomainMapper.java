package com.telesoftas.onboarding.persistence.librarian.service;

import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.persistence.librarian.entity.LibrarianEntity;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityToDomainMapper {

    public Librarian toLibrarianModel(@NonNull LibrarianEntity entity) {

        return Librarian.builder()
            .id(entity.getId())
            .firstname(entity.getFirstname())
            .lastname(entity.getLastname())
            .email(entity.getEmail())
            .password(entity.getPassword())
            .timeCreated(entity.getTimeCreated())
            .timeModified(entity.getTimeModified())
            .build();
    }


    public LibrarianEntity toLibrarianEntity(@NonNull Librarian librarian) {

        return LibrarianEntity.builder()
            .id(librarian.getId())
            .firstname(librarian.getFirstname())
            .lastname(librarian.getLastname())
            .email(librarian.getEmail())
            .password(librarian.getPassword())
            .timeCreated(librarian.getTimeCreated())
            .timeModified(librarian.getTimeModified())
            .build();
    }
}
