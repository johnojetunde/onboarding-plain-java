package com.telesoftas.onboarding.app.database;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection initializeDatabaseConnection(String databaseUrl) throws SQLException {

        return DriverManager.
            getConnection(databaseUrl);
    }
}
