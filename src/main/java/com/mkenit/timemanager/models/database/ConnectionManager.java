package com.mkenit.timemanager.models.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private final static String USERNAME_KEY = "db.username";
    private final static String PASSWORD_KEY = "db.password";
    private final static String URL_KEY = "db.url";

    private ConnectionManager() {
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesDB.getProperty(URL_KEY),
                    PropertiesDB.getProperty(USERNAME_KEY),
                    PropertiesDB.getProperty(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
