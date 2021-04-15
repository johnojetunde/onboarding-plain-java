package com.telesoftas.onboarding.persistence.librarian.repository;

import com.telesoftas.onboarding.domain.librarian.exception.LibrarianSaveException;
import com.telesoftas.onboarding.domain.librarian.exception.LibrarianUniqueEmailCheckException;
import com.telesoftas.onboarding.domain.librarian.model.Librarian;
import com.telesoftas.onboarding.domain.librarian.registration.LibrarianEmailExistence;
import com.telesoftas.onboarding.domain.librarian.registration.Storage;
import com.telesoftas.onboarding.persistence.librarian.entity.LibrarianEntity;
import com.telesoftas.onboarding.persistence.librarian.service.EntityToDomainMapper;
import lombok.AllArgsConstructor;

import java.sql.*;


@AllArgsConstructor
public class MySQLRegistration implements Storage, LibrarianEmailExistence {

    private final Connection connection;

    @Override
    public boolean isEmailExisting(String email) throws LibrarianUniqueEmailCheckException {
        String sqlQuery = "select * from librarian where `email`=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, email);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new LibrarianUniqueEmailCheckException("Unable to check the uniqueness of email", e);
        }
    }


    @Override
    public void save(Librarian librarian) throws LibrarianSaveException {
        LibrarianEntity librarianEntity = EntityToDomainMapper.toLibrarianEntity(librarian);

        String sqlQuery = "insert into librarian " +
            "(firstname,lastname,email,password,time_created)" +
            "VALUES(?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, librarianEntity.getFirstname());
            statement.setString(2, librarianEntity.getLastname());
            statement.setString(3, librarianEntity.getEmail());
            statement.setString(4, librarianEntity.getPassword());
            statement.setTimestamp(5, Timestamp.valueOf(librarianEntity.getTimeCreated()));


            if (statement.executeUpdate() != 1) {
                throw new LibrarianSaveException("Unable to persist librarian data into the database");
            }
        } catch (SQLException e) {
            throw new LibrarianSaveException("SQLError persisting librarian data into the database", e);
        }
    }
}
