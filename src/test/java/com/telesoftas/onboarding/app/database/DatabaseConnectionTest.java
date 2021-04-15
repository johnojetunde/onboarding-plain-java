package com.telesoftas.onboarding.app.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseConnectionTest{


    @Test
    void test_database_connection()throws SQLException {
        Connection connection = DatabaseConnection.initializeDatabaseConnection(
            "jdbc:mysql://127.0.0.1:3306/onboarding?user=user&password=password");
        assertNotNull(connection);

    }



    @Test
    void test_database_connection_failed_if_connection_details_invalid() {

        Assertions.assertThrows(SQLException.class, () -> {
            Connection connection = DatabaseConnection.initializeDatabaseConnection(
                "jdbc:mysql://127.0.0.1:3306/onboarding?user=user&passhjgtntt");
        });
    }
}
