package com.telesoftas.onboarding.persistence.librarian.repository;

import com.telesoftas.onboarding.domain.librarian.exception.LibrarianServiceException;
import com.telesoftas.onboarding.domain.librarian.login.LibrarianFindByEmail;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.persistence.librarian.service.EntityToDomainMapper;
import com.telesoftas.onboarding.persistence.librarian.service.ResultSetToEntityMapper;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@RequiredArgsConstructor
public class LibrarianFindByEmailMySQL implements LibrarianFindByEmail {

    private final Connection connection;

    @Override
    public Optional<Librarian> findByEmail(String email) throws LibrarianServiceException {
        String sqlQuery = "select * from librarian where `email`=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, email);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return ResultSetToEntityMapper.toEntity(rs).map(EntityToDomainMapper::toLibrarianModel);
            }
        } catch (SQLException e) {
            throw new LibrarianServiceException("Unable to retrieve librarian by email", e);
        }
    }
}
