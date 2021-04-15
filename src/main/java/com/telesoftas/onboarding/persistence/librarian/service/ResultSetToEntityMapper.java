package com.telesoftas.onboarding.persistence.librarian.service;

import com.telesoftas.onboarding.persistence.librarian.entity.LibrarianEntity;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@UtilityClass
public class ResultSetToEntityMapper {

    public Optional<LibrarianEntity> toEntity(@NonNull ResultSet rs) throws SQLException {
        if (rs.next()) {
            return Optional.of(LibrarianEntity.builder()
                .id(rs.getLong("id"))
                .firstname(rs.getString("firstname"))
                .lastname(rs.getString("lastname"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .timeCreated(rs.getTimestamp("time_created").toLocalDateTime())
                .timeModified(
                    (rs.getTimestamp("time_modified") == null) ?
                        null : rs.getTimestamp("time_modified").toLocalDateTime())
                .build());
        }
        return Optional.empty();
    }
}
